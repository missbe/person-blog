package cn.missbe.missbe_www.util;

/**
 * @author lyg
 * @date 16/9/1 12:24
 */
public class ThreadUtil {
    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
