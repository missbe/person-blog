package cn.missbe.missbe_www.util;

import cn.missbe.missbe_www.entity.SystemLog.Level;

import java.io.*;

/**
 * 序列化工具
 *
 * @author liaoxing
 * @date 2016年7月29日 上午4:54:51
 */
@SuppressWarnings("unchecked")
public class SerializeUtil {

    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            PrintUtil.print("serialize error:" + e, Level.error);
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }

    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    @SafeVarargs
    public static <T> T deserialize(byte[] in, Class<T>... requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrintUtil.print("deserialize error:" + e, Level.error);
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }

    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
                PrintUtil.print("close stream error:" + e, Level.error);
            }
    }

}
