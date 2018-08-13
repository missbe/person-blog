package cn.missbe.missbe_www.shiro;

import cn.missbe.missbe_www.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author liaoxing
 * @date 2016年7月27日 下午3:15:39
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {

    private ShiroSessionRepository shiroSessionRepository;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        getShiroSessionRepository().saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            return;
        }
        Serializable id = session.getId();
        if (id != null)
            getShiroSessionRepository().deleteSession(id);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSessions();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        getShiroSessionRepository().saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return getShiroSessionRepository().getSession(sessionId);
    }
}
