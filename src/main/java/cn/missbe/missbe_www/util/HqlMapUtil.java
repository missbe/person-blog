package cn.missbe.missbe_www.util;

import cn.missbe.missbe_www.entity.SystemLog.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数转换到Map，适配hql占位符
 *
 * @author lyg
 * @date 2016年7月29日 上午4:48:00
 */
public class HqlMapUtil {

    /**
     * 参数转换到Map，适配hql占位符
     *
     * @param paramNames 参数名称多个参数以","分隔
     * @param params     参数值，多个参数连续写，顺序与上面对应好
     * @return
     */
    public static Map<String, Object> paramsToMap(String paramNames, Object... params) {
        String[] paramNs = paramNames.split(",");
        if (paramNs.length != params.length) {
            PrintUtil.print("参数个数不匹配!" + paramNs.length + ":" + params.length, Level.error);
            return null;
        }
        if (paramNs.length == 0) {
            return null;
        }
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < params.length; i++) {
            paramMap.put(paramNs[i], params[i]);
        }
        return paramMap;
    }
}
