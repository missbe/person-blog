package cn.missbe.missbe_www.listener;

import cn.missbe.missbe_www.entity.SystemConfig;
import cn.missbe.missbe_www.exception.SystemConfigException;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 系统配置
 *
 * @author lyg
 * @date 2016年7月25日 下午3:53:07
 */
@Service
public class AppConfig {

    private static Properties properties = new Properties();
    private static final Object reloadLock = new Object();

    // 每十分钟重载一次配置文件
    @Scheduled(cron = "0 */10 * * * ?")
    public void reloadConfig() {
        synchronized (reloadLock) {
            properties = initProperties();
        }
        syncInDb(properties);
    }

    private void syncInDb(Properties properties) {
        if (SystemConfig.service != null)
            SystemConfig.service.syncConfig(trasnateToConfigs(properties));
    }

    /**
     * 将读取的配置文件，转化为SystemConfig实体
     *
     * @param properties
     * @return
     */
    private static List<SystemConfig> trasnateToConfigs(Properties properties) {
        List<String> keys = new ArrayList<>();
        List<SystemConfig> configs = new ArrayList<>();
        for (Object key : properties.keySet()) {
            keys.add(key.toString());
        }
        for (Entry<Object, Object> kv : properties.entrySet()) {
            SystemConfig config = new SystemConfig();
            String value = kv.getValue().toString();
            String key = kv.getKey().toString();
            config.setId(key);
            // 引入性配置
            while (value.contains("${")) {
                int start = value.indexOf("${");
                int end = value.indexOf("}");
                String mid = value.substring(start, end);
                value = value.substring(0, start) + properties.getProperty(mid.substring(2, mid.length()), mid + "}")
                        + value.substring(end + 1, value.length());
            }
            config.setValue(value);
            if (keys.contains(key + "." + App.IMPORTANT_CONFIG_DESCRIPTION_SUFFIX)) {
                // 关键配置
                config.setImportant(true);
                config.setText(properties.getProperty(key + "." + App.IMPORTANT_CONFIG_DESCRIPTION_SUFFIX));
            } else if (key.endsWith(App.IMPORTANT_CONFIG_DESCRIPTION_SUFFIX)) {
                continue;
            }
            configs.add(config);
        }
        return configs;
    }

    /**
     * 载入系统配置文件
     */
    private static Properties initProperties() {
        Properties properties = new Properties();
        InputStream appConfigFileStream = null;
        InputStream tagTextFileStream = null;
        try {
            // 全局配置文件
            appConfigFileStream = AppConfig.class.getClassLoader().getResourceAsStream(App.APP_CONFIG_FILE);
            if (appConfigFileStream != null)
                properties.load(appConfigFileStream);
            // 中文配置文件
            tagTextFileStream = AppConfig.class.getClassLoader().getResourceAsStream(App.APP_MSG_ZHCN_FILE);
            if (tagTextFileStream != null)
                properties.load(tagTextFileStream);
        } catch (Exception e) {
            e.printStackTrace();
            PrintUtil.print("载入系统配置文件错误：" + e.getMessage(), Level.error);
        } finally {
            try {
                if (appConfigFileStream != null)
                    appConfigFileStream.close();
                if (tagTextFileStream != null)
                    tagTextFileStream.close();
            } catch (Exception e2) {
                PrintUtil.print("关闭系统配置文件流错误：" + e2.getMessage(), Level.error);
            }
        }
        return properties;
    }

    /**
     * 根据配置的key得到value
     *
     * @param key
     * @return value 配置不存在返回key
     */
    public static String getConfigAsString(String key) {
        String strConf = readConfig(key);
        if (strConf == null) {
            new SystemConfigException("配置不存在:" + key).printStackTrace();
        }
        return strConf;
    }

    /**
     * 根据配置的key得到Bool类型value
     *
     * @param key
     * @return value 配置不存在或异常返回false
     */
    public static boolean getConfigAsBoolean(String key) {
        return "true".equals(getConfigAsString(key));
    }

    /**
     * 根据配置的key得到Int类型value
     *
     * @param key
     * @return value 配置不存在或异常返回0
     */
    public static int getConfigAsInt(String key) {
        String StrConf = readConfig(key);
        if (key == null || key.equals(StrConf)) {
            return 0;
        }
        int res = 0;
        try {
            res = Integer.valueOf(StrConf);
        } catch (Exception e) {
            new SystemConfigException("配置不能转化为int:" + key).printStackTrace();
        }
        return res;
    }

    /**
     * 根据配置的key得到double类型value
     *
     * @param key
     * @return value 配置有误返回0
     */
    public static double getConfigAsDouble(String key) {
        String StrConf = readConfig(key);
        if (key == null || key.equals(StrConf)) {
            return 0;
        }
        double res = 0;
        try {
            res = Double.valueOf(StrConf);
        } catch (Exception e) {
            new SystemConfigException("配置不能转化为double:" + key).printStackTrace();
        }
        return res;
    }

    /**
     * 优先从Redis读取配置，然后从数据库读取配置,再其次则读取配置文件!
     *
     * @param key
     * @return 配置的值或者null
     */
    @SuppressWarnings("unchecked")
    private static String readConfig(String key) {

        // 从数据库读取
        SystemConfig configInDb = SystemConfig.service.findById(key);
        if (configInDb != null) {
            // 向redis加入该key
            PrintUtil.logger.warn("获取了数据库中存在的配置:" + key );
            return configInDb.getValue();
        } else {
            String configInFile = properties.getProperty(key);
            if (configInFile == null) {
                return "";
            } else {
                // 与数据库同步
                PrintUtil.logger.warn("获取了文件中存在但数据库没有的配置:" + key + " ,写入该配置到数据库.");
                SystemConfig config = new SystemConfig();
                config.setId(key);
                config.setValue(configInFile);
                if (properties.containsKey(key + ".importDescription")) {
                    // 关键配置
                    config.setImportant(true);
                    config.setText(properties.getProperty(key + ".importDescription"));
                }
                SystemConfig.service.save(config);
                return configInFile;
            }
        }
    }

}