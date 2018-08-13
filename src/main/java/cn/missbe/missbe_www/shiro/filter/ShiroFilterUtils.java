package cn.missbe.missbe_www.shiro.filter;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

public class ShiroFilterUtils {
    final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;


    /**
     * 判断是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("accept") != null && request.getHeader("accept").contains("application/json") || request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest");
    }

    /**
     * 向用户输出Json
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void out(ServletResponse response, JsonBaseResult result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(JSON.toJSONString(result));
        out.flush();
        out.close();
    }

    public static String getPermissionUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        while (uri.contains("//")) {
            uri = uri.replaceAll("//", "/");
        }
        if (uri.contains(";")) {
            uri = uri.split(";")[0];
        }
        uri = uri.replaceFirst("/", "");
        if (uri.endsWith("UI")) {
            uri = uri.substring(0, uri.length() - 2);
        }
        return uri;
    }
}
