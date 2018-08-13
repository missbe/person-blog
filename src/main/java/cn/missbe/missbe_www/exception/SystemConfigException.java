package cn.missbe.missbe_www.exception;

import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.util.PrintUtil;

/**
 * 系统配置异常
 *
 * @author liaoxing
 * @date 2016年7月25日 下午3:27:35
 */
public class SystemConfigException extends Exception {

    public SystemConfigException(String msg) {
        super(msg);
        PrintUtil.print(msg, Level.error);
    }

    private static final long serialVersionUID = 749070221472541731L;

}
