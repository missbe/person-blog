package cn.missbe.missbe_www.shiro.filter;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.shiro.token.manager.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证是否登录的过滤器
 *
 * @author missbe
 * @date 2016年7月27日 下午7:58:17
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        User token = TokenManager.getToken();

//        PrintUtil.print("当前用户:"+token.toString());

        if (null != token || isLoginRequest(request, response)) {
            return Boolean.TRUE;
        }
        if (ShiroFilterUtils.isAjaxRequest((HttpServletRequest) request)) {// ajax请求
            PrintUtil.print("当前用户没有登录，并且是Ajax请求！");
            ShiroFilterUtils.out(response, new JsonBaseResult("登录超时.请重新登录", false));
        }
        return Boolean.FALSE;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        PrintUtil.print("LoginFilter未通过." + ((HttpServletRequest) request).getRequestURI(), Level.info);
        this.setLoginUrl(App.LOGIN_URL);
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE;
    }

}
