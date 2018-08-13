package cn.missbe.missbe_www.util;

import cn.missbe.missbe_www.entity.SystemLog.Level;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 反射工具集
 *
 * @author ly
 */
public class TypeUtils {

    /**
     * 获取clazz实体类的主键类型
     *
     * @param clazz
     * @return
     */
    public static Class<?> getPkClass(Class<?> clazz) {
        Class<?> pkClazz = null;
        if (clazz == null) {
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Id id = method.getAnnotation(Id.class);
            if (id != null) {
                pkClazz = method.getReturnType();
                break;
            }
        }
        if (pkClazz == null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Id id = field.getAnnotation(Id.class);
                if (id != null) {
                    pkClazz = field.getType();
                    break;
                }
            }
        }
        return pkClazz;
    }

    public static boolean isIntClass(Type type) {
        return (isPrimitiveClassByClass(type, Integer.TYPE)) || (isPrimitiveClassByClass(type, Integer.class));
    }

    public static boolean isLongClass(Type type) {
        return (isPrimitiveClassByClass(type, Long.TYPE)) || (isPrimitiveClassByClass(type, Long.class));
    }

    public static boolean isPrimitiveClassByClass(Type type, Class<?> clazz) {
        return type != null && type.toString().equals(clazz.toString());
    }

    /**
     * @param value 属性值
     * @param type  要转化成的Type,基础属性可行.
     * @return 转化后的值，为空表示无法转化
     */
    public static Object stringToFieldValve(String value, Type type) {
        Object result = null;
        String typeString = type.toString();
        try {
            switch (typeString) {
                case "class java.lang.String":
                    result = value;
                    break;
                case "long":
                    result = Long.valueOf(value);
                    break;
                case "int":
                    result = Integer.valueOf(value);
                    break;
                case "class java.util.Date":
                    result = DateUtil.parseDate(value);
                    break;
                case "class java.lang.Boolean":
                    result = Boolean.parseBoolean(value);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            PrintUtil.print("转化异常:" + value + " to " + typeString);
        }
        return result;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz =
                // clazz.getSuperclass(),最后就不会进入到父类中了
                // PrintUtil.print("出现获取属性字段异常");
            }
        }
        return null;
    }

    public static boolean isNotBaseType(Type type) {
        String typeStr = type.toString();
        return !typeStr.matches("(int|byte|short|long|boolean|char|float|double)") && !typeStr.matches("class\\sjava\\.lang\\.String") && !typeStr.matches("class\\sjava\\.util\\.Date");
    }

    /**
     * 根据属性名设置对应Field的值
     *
     * @param name  属性名
     * @param o     赋值对象
     * @param value 值
     * @return 操作结果
     */
    public static boolean setFieldByName(String name, Object o, Object value) {
        try {
            Field f = getDeclaredField(o, name);
            if (f != null) {
                f.setAccessible(true);
                f.set(o, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrintUtil.print("赋值失败:" + o.getClass().getSimpleName() + "的" + name + ":" + value, Level.error);
            return false;
        }
        return true;
    }

    /**
     * 拷贝属性值
     *
     * @param src
     * @param des
     */
    public static void copyProperties(Object src, Object des) {
        List<String> srcFieldNames = getBeanPropertyNames(src.getClass());
        List<Field> desFields = getBeanFileds(des.getClass());
        for (Field field : desFields) {
            field.setAccessible(true);
            if (srcFieldNames.contains(field.getName())) {
                try {
                    Object obj = getFieldValue(src, field.getName());
                    if (obj == null) {
                        continue;
                    }
                    setFieldByName(field.getName(), des, obj);
                } catch (Exception e) {
                    PrintUtil.print("拷贝属性时发生未知异常" + e, Level.error);
                }
            }
        }
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        // 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            PrintUtil.print("设置属性值失败," + object.getClass().getSimpleName() + "没有这个属性" + fieldName, Level.warning);
            return;
        }
        // 抑制Java对其的检查
        field.setAccessible(true);
        try {
            // 将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        // 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        // 抑制Java对其的检查
        if (field == null) {
            PrintUtil.print("读取属性值失败," + object.getClass().getSimpleName() + "没有这个属性" + fieldName, Level.warning);
            return null;
        }
        field.setAccessible(true);
        try {
            // 获取 object 中 field 所代表的属性值
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 循环向上获取 获取该类型的所有字段名称列表，不包括final修饰的字段
     *
     * @param clazz 类型
     * @return
     */
    public static List<String> getBeanPropertyNames(Class<?> clazz) {
        List<String> results = new ArrayList<>();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; fields != null && i < fields.length; i++) {
                Field field = fields[i];
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                results.add(field.getName());
            }
        }
        return results;
    }

    public static List<Field> getBeanFileds(Class<?> clazz) {
        List<Field> results = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; fields != null && i < fields.length; i++) {
                Field field = fields[i];
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                results.add(field);
            }
        }
        return results;
    }

    public static void copyProperties(Object src, Object des, String... ignoreFields) {
        List<String> srcFieldNames = getBeanPropertyNames(src.getClass());
        List<Field> desFields = getBeanFileds(des.getClass());
        List<String> notcontains = Arrays.asList(ignoreFields);
        for (Field field : desFields) {
            field.setAccessible(true);
            if (srcFieldNames.contains(field.getName()) && !notcontains.contains(field.getName())) {
                try {
                    Object obj = getFieldValue(src, field.getName());
                    if (obj == null) {
                        continue;
                    }
                    setFieldByName(field.getName(), des, obj);
                } catch (Exception e) {
                    PrintUtil.print("拷贝属性时发生未知异常" + e, Level.error);
                }
            }
        }
    }

    /**
     * 获取重复值字段
     *
     * @param obj  被检查对象
     * @param objs 对比对象集合
     * @return 有重复值的字段
     */
    public static List<String> getReapeatFields(Object obj, @SuppressWarnings("rawtypes") Collection objs) {
        List<String> result = new ArrayList<>();
        List<Field> fields = getBeanFileds(obj.getClass());
        for (Field field : fields) {
            boolean same = false;
            Object value = getFieldValue(obj, field.getName());
            for (Object o : objs) {
                Object value2 = getFieldValue(o, field.getName());
                if (value != null) {
                    if (value.equals(value2))
                        same = true;
                } else {
                    if (value2 == null) {
                        same = true;
                    }
                }
            }
            if (same)
                result.add(field.getName());
        }
        return result;
    }

}
