package cn.missbe.missbe_www.util;

/**
 * @author liaoxing
 * @date 16/8/16 19:15
 */

import cn.missbe.missbe_www.entity.SystemLog.Level;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class HttpServletUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.split(",")[0];
    }

    public static String getIpAddr(ServerHttpRequest request) {
        List<String> xforwardedfors = request.getHeaders().get("x-forwarded-for");
        String ip = xforwardedfors != null ? xforwardedfors.get(0) : "";
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().get("Proxy-Client-IP") == null ? ""
                    : request.getHeaders().get("Proxy-Client-IP").get(0);
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().get("WL-Proxy-Client-IP") == null ? ""
                    : request.getHeaders().get("WL-Proxy-Client-IP").get(0);
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().toString();
        }
        return ip.split(",")[0];
    }

    public static void printAllParams(HttpServletRequest request) {
        for (Entry<String, String[]> kv : request.getParameterMap().entrySet()) {
            PrintUtil.print(kv.getKey() + ":" + Arrays.toString(kv.getValue()), Level.debug);
        }
    }

    /**
     * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
     *
     * @param request http请求
     * @return 如果命中手机特征规则，则返回对应的特征字符串
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = {"iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile", "micromessenger"};
        if (request.getHeader("User-Agent") != null) {
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().contains(mobileAgent)) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;

    }
}
