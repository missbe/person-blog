package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dao.SystemConfigDao;
import cn.missbe.missbe_www.entity.SystemConfig;
import cn.missbe.missbe_www.listener.AppConfig;
import cn.missbe.missbe_www.service.SystemConfigService;
import cn.missbe.missbe_www.shiro.cache.RedisCache;
import cn.missbe.missbe_www.util.PrintUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    @Resource
    private SystemConfigDao systemLogDao;

    @Override
    public void syncConfig(List<SystemConfig> configsInfile) {
        boolean onlineMode = AppConfig.getConfigAsBoolean("system.mode.online");
        // 获取数据库中的配置
        List<SystemConfig> dbConfigs = systemLogDao.findAll();
        Map<String, SystemConfig> configInDbMap = new HashMap<>();
        for (SystemConfig systemConfig : dbConfigs) {
            configInDbMap.put(systemConfig.getId(), systemConfig);
            if (!configsInfile.contains(systemConfig)) {
                // 如果数据库有多余的配置删除之
                PrintUtil.logger.warn("系统发现多余的配置项,删除:" + systemConfig.getId() + "@" + systemConfig.getValue());
                systemLogDao.delete(systemConfig);
                RedisCache.delByKey(App.REDIS_CONFIG_CACHE_PRE + systemConfig.getId());
            } else {
                // 总是刷新与Redis的同步
                RedisCache.set(App.REDIS_CONFIG_CACHE_PRE + systemConfig.getId(), systemConfig.getValue());
            }
        }
        for (SystemConfig configInfile : configsInfile) {
            if (!dbConfigs.contains(configInfile)) {
                // 如果数据库中不存在则添加到数据库
                PrintUtil.logger.warn("系统发现新增的配置项,添加:" + configInfile.getId() + "@" + configInfile.getValue());
                this.save(configInfile);
                RedisCache.set(App.REDIS_CONFIG_CACHE_PRE + configInfile.getId(), configInfile.getValue());
            } else {
                // 数据库中存在且非线上模式，则以配置文件为准
                if (!onlineMode
                        && !configInDbMap.get(configInfile.getId()).getValue().equals(configInfile.getValue())) {
                    SystemConfig dbUpConfig = configInDbMap.get(configInfile.getId());
                    dbUpConfig.setImportant(configInfile.isImportant());
                    dbUpConfig.setText(configInfile.getText());
                    dbUpConfig.setValue(configInfile.getValue());
                    systemLogDao.update(dbUpConfig);
                    RedisCache.set(App.REDIS_CONFIG_CACHE_PRE + dbUpConfig.getId(), dbUpConfig.getValue());
                }
            }
        }
    }

    @Override
    public SystemConfig findById(String key) {
        return systemLogDao.findById(key);
    }

    @Override
    public void save(SystemConfig config) {
        systemLogDao.save(config);
    }

}
