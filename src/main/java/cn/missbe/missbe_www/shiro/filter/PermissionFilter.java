package cn.missbe.missbe_www.shiro.filter;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.SystemLog.Level;
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
 * 验证是否有URL权限的过滤器
 *
 * @author missbe
 * @date 2016年7月27日 下午7:59:58
 */
public class PermissionFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        // 先判断带参数的权限判断
        Subject subject = getSubject(request, response);
        if (null != mappedValue) {
            String[] arra = (String[]) mappedValue;
            for (String permission : arra) {
                if (subject.isPermitted(permission)) {
                    return Boolean.TRUE;
                }
            }
        }
        // 取到请求的uri ，进行权限判断
        String uri = ShiroFilterUtils.getPermissionUrl((HttpServletRequest) request);
        PrintUtil.print(uri+"的权限！");
        if (subject.isPermitted(uri) || App.ON_FIRE_ACCOUNT.equals(((User) subject.getPrincipal()).getAccount())) {
            return Boolean.TRUE;
        }
        if (ShiroFilterUtils.isAjaxRequest((HttpServletRequest) request)) {
            PrintUtil.print("没有该URL的权限！");
            ShiroFilterUtils.out(response, new JsonBaseResult("没有该URL的权限.", false));
        }
        PrintUtil.print("没有该"+uri+"的权限！");
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintUtil.print("PermissionFilter未通过." + ((HttpServletRequest) request).getRequestURI(), Level.info);
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {// 表示没有登录，重定向到登录页面
            this.setLoginUrl(App.LOGIN_URL);
            saveRequestAndRedirectToLogin(request, response);
        } else {
            if (StringUtils.hasText(App.UNAUTHORIZED)) {// 如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response, App.UNAUTHORIZED);
            } else {// 否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return Boolean.FALSE;
    }

}
