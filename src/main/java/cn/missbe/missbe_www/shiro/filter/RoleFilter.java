package cn.missbe.missbe_www.shiro.filter;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.util.PrintUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author missbe
 * @date 2016年7月27日 下午7:34:10
 */
public class RoleFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String[] arra = (String[]) mappedValue;

        Subject subject = getSubject(request, response);
        for (String role : arra) {
            if (subject.hasRole("role:" + role)) {
                return true;
            }
        }
        if (App.ON_FIRE_ACCOUNT.equals(((User) subject.getPrincipal()).getAccount())) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintUtil.print("RoleFilter未通过." + ((HttpServletRequest) request).getRequestURI(), SystemLog.Level.info);
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {// 表示没有登录，重定向到登录页面
            this.setLoginUrl(App.LOGIN_URL);
            saveRequestAndRedirectToLogin(request, response);
        } else {
            if (StringUtils.hasText(App.UNAUTHORIZED)) {// 如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response, App.UNAUTHORIZED);
            } else {// 否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

}
