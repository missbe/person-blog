package cn.missbe.missbe_www.shiro.cache.impl;

import cn.missbe.missbe_www.shiro.cache.JedisManager;
import cn.missbe.missbe_www.shiro.cache.JedisShiroCache;
import cn.missbe.missbe_www.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;

/**
 * @author missbe
 * @date 2016年7月27日 下午3:20:30
 */
public class JedisShiroCacheManager implements ShiroCacheManager {

    private JedisManager jedisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    @Override
    public void destroy() {
        // 如果和其他系统，或者应用在一起就不能关闭
        // getJedisManager().getJedis().shutdown();
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
