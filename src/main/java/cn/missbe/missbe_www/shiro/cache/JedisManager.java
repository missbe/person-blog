package cn.missbe.missbe_www.shiro.cache;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.util.SerializeUtil;
import org.apache.shiro.session.Session;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author missbe
 * @date 2016年7月27日 下午3:21:00
 */
public class JedisManager {

    private JedisPool jedisPool;

    public Jedis getJedis() {
        Jedis jedis;
        try {
            jedis = getJedisPool().getResource();
        } catch (Exception e) {
            throw new JedisConnectionException(e);
        }
        return jedis;
    }

    public void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        jedis.close();
    }

    public byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        byte[] result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }

    public void deleteByKey(int dbIndex, byte[] key) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.del(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            if (expireTime > 0)
                jedis.expire(key, expireTime);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取所有Session
     *
     * @param dbIndex
     * @param redisShiroSession
     * @return
     * @throws Exception
     */
    public Collection<Session> AllSession(int dbIndex, String redisShiroSession) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        Set<Session> sessions = new HashSet<>();
        try {
            jedis = getJedis();
            jedis.select(dbIndex);

            Set<byte[]> byteKeys = jedis.keys(("*" + App.REDIS_SHIRO_SESSION + "*").getBytes());
            if (byteKeys != null && !byteKeys.isEmpty()) {
                for (byte[] bs : byteKeys) {
                    Session obj = SerializeUtil.deserialize(jedis.get(bs), Session.class);
                    if (obj != null) {
                        sessions.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return sessions;
    }
}
