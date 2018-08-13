package cn.missbe.missbe_www.shiro.filter;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.shiro.session.CustomSessionManager;
import cn.missbe.missbe_www.shiro.session.SessionStatus;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 简单验证过滤器，验证是否登录被踢出了
 *
 * @author liaoxing
 * @date 2016年7月27日 下午7:57:59
 */
public class SimpleAuthFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        SessionStatus sessionStatus = (SessionStatus) session.getAttribute(CustomSessionManager.SESSION_STATUS);
        if (null != sessionStatus && !sessionStatus.isOnlineStatus()) {
            // 判断是不是Ajax请求
            if (ShiroFilterUtils.isAjaxRequest((HttpServletRequest) request)) {
                PrintUtil.print("当前用户已经被踢出，并且是Ajax请求！", Level.debug);
                ShiroFilterUtils.out(response, new JsonBaseResult("您已经被踢出登录！", false));
            }
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintUtil.print("SimpleAuthFilter没通过." + ((HttpServletRequest) request).getRequestURI(), Level.info);
        // 先退出其session
        Subject subject = getSubject(request, response);
        subject.logout();
        WebUtils.getSavedRequest(request);
        // 再重定向
        WebUtils.issueRedirect(request, response, App.KICKED_OUT);
        return false;
    }

}
