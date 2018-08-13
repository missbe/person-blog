package cn.missbe.missbe_www.shiro.cache;

import cn.missbe.missbe_www.shiro.session.SessionStatus;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.shiro.session.CustomSessionManager;
import cn.missbe.missbe_www.shiro.session.ShiroSessionRepository;
import cn.missbe.missbe_www.util.SerializeUtil;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * Session 管理
 *
 * @author liaoxing
 * @date 2016年7月27日 下午3:28:24
 */
public class JedisShiroSessionRepository implements ShiroSessionRepository {

    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private JedisManager jedisManager;

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));

            // 不存在才添加。
            if (null == session.getAttribute(CustomSessionManager.SESSION_STATUS)) {
                // Session 踢出自存存储。
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }

            byte[] value = SerializeUtil.serialize(session);
            long sessionTimeOut = session.getTimeout() / 1000;
            Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
            getJedisManager().saveValueByKey(App.REDIS_DB_INDEX, key, value, expireTime.intValue());
        } catch (Exception e) {
            PrintUtil.print("save session error，id:[" + session.getId() + "]", Level.error);
        }
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(App.REDIS_DB_INDEX, SerializeUtil.serialize(buildRedisSessionKey(id)));
        } catch (Exception e) {
            PrintUtil.print("删除session出现异常，id:[" + id + "]", Level.error);
        }
    }

    @Override
    public Session getSession(Serializable id) {
        if (id == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(App.REDIS_DB_INDEX,
                    SerializeUtil.serialize(buildRedisSessionKey(id)));
            session = SerializeUtil.deserialize(value, Session.class);
        } catch (Exception e) {
            PrintUtil.print("获取session异常，id:[" + id + "]", Level.error);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        Collection<Session> sessions = null;
        try {
            sessions = getJedisManager().AllSession(App.REDIS_DB_INDEX, App.REDIS_SHIRO_SESSION);
        } catch (Exception e) {
            PrintUtil.print("获取全部session异常", Level.error);
        }

        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return App.REDIS_SHIRO_SESSION + sessionId;
    }

    private JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
