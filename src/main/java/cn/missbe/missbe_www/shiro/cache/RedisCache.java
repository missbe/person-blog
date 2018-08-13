package cn.missbe.missbe_www.shiro.cache;

import cn.missbe.missbe_www.listener.InitServletContextListener;
import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.util.SerializeUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RedisCache {

    private final static JedisManager J = InitServletContextListener.getApplicationContext()
                                           .getBean("jedisManager", JedisManager.class);

    private RedisCache() {
    }

    /**
     * 简单的Get
     *
     * @param <T>
     * @param key
     * @param requiredType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T>... requiredType) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] skey = SerializeUtil.serialize(key);
            return SerializeUtil.deserialize(jds.get(skey), requiredType);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * 简单的set
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] skey = SerializeUtil.serialize(key);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.set(skey, svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
    }

    /**
     * @param <T>
     * @param mapkey       map
     * @param key          map里的key
     * @param requiredType value的泛型类型
     * @return
     */
    public static <T> T getVByMap(String mapkey, String key, Class<T> requiredType) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] mkey = SerializeUtil.serialize(mapkey);
            byte[] skey = SerializeUtil.serialize(key);
            List<byte[]> result = jds.hmget(mkey, skey);
            if (null != result && result.size() > 0) {
                byte[] x = result.get(0);
                return SerializeUtil.deserialize(x, requiredType);
            }

        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * @param mapkey map
     * @param key    map里的key
     * @param value  map里的value
     */
    public static void setVByMap(String mapkey, String key, Object value) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] mkey = SerializeUtil.serialize(mapkey);
            byte[] skey = SerializeUtil.serialize(key);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.hset(mkey, skey, svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }

    }

    /**
     * 删除Map里的值
     *
     * @param mapKey
     * @param dkey
     * @return
     */
    public static Object delByMapKey(String mapKey, String... dkey) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[][] dx = new byte[dkey.length][];
            for (int i = 0; i < dkey.length; i++) {
                dx[i] = SerializeUtil.serialize(dkey[i]);
            }
            byte[] mkey = SerializeUtil.serialize(mapKey);
            return jds.hdel(mkey, dx);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return 0;
    }

    /**
     * 往redis里取set整个集合
     *
     * @param <T>
     * @param setKey
     * @param requiredType
     * @return
     */
    public static <T> Set<T> getVByList(String setKey, Class<T> requiredType) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] lkey = SerializeUtil.serialize(setKey);
            Set<T> set = new TreeSet<>();
            Set<byte[]> xx = jds.smembers(lkey);
            for (byte[] bs : xx) {
                T t = SerializeUtil.deserialize(bs, requiredType);
                set.add(t);
            }
            return set;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * 获取Set长度
     *
     * @param setKey
     * @return
     */
    public static Long getLenBySet(String setKey) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            return jds.scard(setKey);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * 删除Set
     *
     * @param dkey
     * @return
     */
    public static Long delSetByKey(String key, String... dkey) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            Long result;
            if (null == dkey) {
                result = jds.srem(key);
            } else {
                result = jds.del(key);
            }
            return result;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return 0L;
    }

    /**
     * 随机 Set 中的一个值
     *
     * @param key
     * @return
     */
    public static String srandmember(String key) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            return jds.srandmember(key);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * 往redis里存Set
     *
     * @param setKey
     * @param value
     */
    public static void setVBySet(String setKey, String value) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            jds.sadd(setKey, value);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
    }

    /**
     * 取set
     *
     * @param key
     * @return
     */
    public static Set<String> getSetByKey(String key) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            return jds.smembers(key);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;

    }

    /**
     * 往redis里存List
     *
     * @param listKey
     * @param value
     */
    public static void setVByList(String listKey, Object value) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] lkey = SerializeUtil.serialize(listKey);
            byte[] svalue = SerializeUtil.serialize(value);
            jds.rpush(lkey, svalue);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
    }

    /**
     * 往redis里取list
     *
     * @param <T>
     * @param listKey
     * @param start
     * @param end
     * @param requiredType
     * @return
     */
    public static <T> List<T> getVByList(String listKey, int start, int end, Class<T> requiredType) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] lkey = SerializeUtil.serialize(listKey);
            List<T> list = new ArrayList<>();
            List<byte[]> xx = jds.lrange(lkey, start, end);
            for (byte[] bs : xx) {
                T t = SerializeUtil.deserialize(bs, requiredType);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * 获取list长度
     *
     * @param listKey
     * @return
     */
    public static Long getLenByList(String listKey) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] lkey = SerializeUtil.serialize(listKey);
            return jds.llen(lkey);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return null;
    }

    /**
     * 删除
     *
     * @param dkey
     * @return
     */
    public static Long delByKey(String... dkey) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[][] dx = new byte[dkey.length][];
            for (int i = 0; i < dkey.length; i++) {
                dx[i] = SerializeUtil.serialize(dkey[i]);
            }
            return jds.del(dx);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return 0L;
    }

    /**
     * 判断是否存在
     *
     * @param existskey
     * @return
     */
    public static boolean exists(String existskey) {
        Jedis jds = null;
        boolean isBroken = false;
        try {
            jds = J.getJedis();
            jds.select(App.REDIS_DB_INDEX);
            byte[] lkey = SerializeUtil.serialize(existskey);
            return jds.exists(lkey);
        } catch (Exception e) {
            isBroken = true;
            e.printStackTrace();
        } finally {
            returnResource(jds, isBroken);
        }
        return false;
    }

    /**
     * 释放
     *
     * @param jedis
     * @param isBroken
     */
    private static void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        jedis.close();
    }
}
