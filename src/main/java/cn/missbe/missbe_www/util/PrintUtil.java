package cn.missbe.missbe_www.util;

/**
 * Created by Administrator on 2016/11/9 0009.
 */

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author liaoxing
 * @Description 日志打印记录工具
 * @date 2016年7月29日 上午4:54:25
 */
public class PrintUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
    public static final Logger logger = Logger.getLogger("printlog");

    /**
     * @param msg
     * @param level0 可选参数，留空则为Level.DEBUG
     * @Description 根据配置，判断是否打印，是否记录到数据库
     */
    public static void print(Object msg, Level... level0) {
        Level level = Level.debug;
        if (level0.length != 0) {
            level = level0[0];
        }
        String msgStr;
        if (msg != null) {
            msgStr = msg.toString();
        } else {
            msgStr = "空值打印.";
        }
        if (msgStr.trim().equals("")) {
            msgStr = "Nothing.";
        }
        StackTraceElement[] temp = Thread.currentThread().getStackTrace();
        StackTraceElement method = temp[2];
        for (StackTraceElement stackTraceElement : temp) {
            if (stackTraceElement.getClassName().contains("police")
                    && !stackTraceElement.getClassName().contains("CGLIB")
                    && !stackTraceElement.getMethodName().equals("print")
                    && !stackTraceElement.getMethodName().equals("around")) {
                method = stackTraceElement;
                break;
            }
        }
        String fromLine = method.getClassName().replaceAll(App.PACKAGE_BASE + ".", "") + "." + method.getMethodName()
                + "() line@" + method.getLineNumber();
        String logMsgStr;
        synchronized (sdf) {
            logMsgStr = sdf.format(Calendar.getInstance().getTime()) + " :" + msgStr + " @来自：" + fromLine;
            // 判断打印日志等级
            if (level.equals(Level.error)
                    || Level.AmoreB(level, Level.strToLevel("debug"))){
                switch (level) {
                    case debug:
                        logger.debug(logMsgStr);
                        break;
                    case info:
                        logger.info(logMsgStr);
                        break;
                    case warning:
                        logger.warn(logMsgStr);
                        break;
                    case error:
                        logger.error(logMsgStr);
                        break;
                    default:
                        logger.debug(logMsgStr);
                }
            }
        }
    }
}

