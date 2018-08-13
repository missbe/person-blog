package cn.missbe.missbe_www;

/**
 * 保存系统的一些固定常量
 *
 * @author missbe
 * @date 2016年7月25日 下午3:31:31
 */
public final class App {

    public static final String PACKAGE_BASE = "cn.missbe.missbe_www";
    /**
     * 系统配置文件
     */
    public static final String APP_CONFIG_FILE = "application.properties";
    /**
     * 中文配置文件
     */
    public static final String APP_MSG_ZHCN_FILE = "message_zh_CN.properties";
    /**
     * 含义性配置后缀，该后缀的配置不会写入数据库，而是作为其描述对象的配置含义解释.<br>
     * 如：system.url.importDescription=系统访问地址，则会作为system.url的含义存入数据库
     */
    public static final String IMPORTANT_CONFIG_DESCRIPTION_SUFFIX = "importDescription";
    /**
     * redis shiro缓存前缀
     */
    public static final String REDIS_SHIRO_CACHE_PRE = "missbe_www_shiro_cache:";
    /**
     * redis systemConfig缓存前缀
     */
    public static final String REDIS_CONFIG_CACHE_PRE = "missbe_www_config_cache:";
    /**
     * session的名称
     */
    public static final String SESSION_NAME = "admin_manager";
    /**
     * LOGIN的名称
     */
    public static final String LOGIN_NAME = "loginUI";
    /**
     * 登录页面url
     */
    public static final String LOGIN_URL = "/loginUI";
    /**
     * 踢出登录提示url
     */
    public final static String KICKED_OUT = "/kickedout";
    /**
     * 没有权限提醒url
     */
    public final static String UNAUTHORIZED = "/unauthorized";
    /**
     * session 缓存 前缀
     */
    public static final String REDIS_SHIRO_SESSION = "missbe_www-shiro-session:";
    /**
     * redis 的库索引号
     */
    public static final int REDIS_DB_INDEX = 8;
    /**
     * 具有所有URL的无限开火权的账号
     */
    public static final String ON_FIRE_ACCOUNT = "admin";
    /**
     * 首页的值key
     */
    public static final String INDEX_JSON = "index";
    /**
     * 关于我们的值key
     */
    public static final String ABOUT_JSON = "about";
    /**
     * 产品服务的值key
     */
    public static final String SERVICES_JSON = "services";
    /**
     * 合作客户的值key
     */
    public static final String CUSTOMS_JSON = "customs";
    /**
     * 联系我们的值key
     */
    public static final String CONTACT_JSON = "contact";

    public static final String SYSTEM_URL = "http://www.missbe.cn";

    public static final String BLOGJSON_PREFIX = "blog#";

}
