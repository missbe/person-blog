package cn.missbe.missbe_www.shiro.cache;

import org.apache.shiro.cache.Cache;

/**
 * @author missbe
 * @date 2016年7月27日 下午10:30:51
 */
public interface ShiroCacheManager {

    <K, V> Cache<K, V> getCache(String name);

    void destroy();

}
