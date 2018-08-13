package cn.missbe.missbe_www.shiro.cache;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.SerializeUtil;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * @param <K>
 * @param <V>
 * @author missbe
 * @date 2016年7月27日 下午3:23:16
 */
public class JedisShiroCache<K, V> implements Cache<K, V> {

    private JedisManager jedisManager;

    private String name;

    public JedisShiroCache(String name, JedisManager jedisManager) {
        this.name = name;
        this.jedisManager = jedisManager;
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(App.REDIS_DB_INDEX, byteKey);
        } catch (Exception e) {
            PrintUtil.print("get value by cache throw exception:" + e, Level.error);
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.saveValueByKey(App.REDIS_DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)),
                    SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
            PrintUtil.print("put cache throw exception:" + e, Level.error);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.deleteByKey(App.REDIS_DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)));
        } catch (Exception e) {
            PrintUtil.print("remove cache throw exception:" + e, Level.error);
        }
        return previos;
    }

    /**
     * 无用，勿用
     */
    @Override
    public void clear() throws CacheException {
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    /**
     * 无用，勿用
     */
    @Override
    public Set<K> keys() {
        return null;
    }

    /**
     * 无用，勿用
     */
    @Override
    public Collection<V> values() {
        return null;
    }

    private String buildCacheKey(Object key) {
        return App.REDIS_SHIRO_CACHE_PRE + getName() + ":" + key;
    }

}
