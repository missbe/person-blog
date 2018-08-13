package cn.missbe.missbe_www.controller;

import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.shiro.filter.ShiroFilterUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lyg
 * @date 16/9/10 00:27
 */
public class BaseController {
    @Autowired
    protected HttpSession session;
    @Autowired
    protected HttpServletRequest request;

    /**
     * 得到会话对象
     * @return
     */
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        session.setMaxInactiveInterval(3600);
        this.session = session;
    }

    /**
     * 得到session的值key对应的值
     * @param key
     * @return
     */
    public Object getSessionValue(String key) {
        return session.getAttribute(key);
    }

    /**
     * 将值放入session中
     * @param key
     * @param value
     */
    public void putToSession(String key, Object value) {
        session.setAttribute(key, value);
    }

    public void removeFromSession(String key) {
        session.removeAttribute(key);
    }

    /**
     * 将当前url放入session中
     */
    public void putUrlToSession() {
        putToSession("currentUrl", ShiroFilterUtils.getPermissionUrl(request));
    }

    /**
     *得到登录用户
     * @return
     */
    public User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}
