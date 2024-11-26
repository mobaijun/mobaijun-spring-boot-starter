package com.mobaijun.translation.util;

import java.lang.reflect.Method;

/**
 * Description: [反射工具类]
 * Author: [mobaijun]
 * Date: [2024/11/26 14:44]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class ReflectUtil {

    /**
     * getter 方法是以 "get" 为前缀的
     */
    private static final String GETTER_PREFIX = "get";

    /**
     * 调用对象的 Getter 方法。
     * 支持多级属性访问，例如：对象名.属性名.属性名
     *
     * @param obj          目标对象
     * @param propertyName 属性名，支持点号分隔的多级属性
     * @return 属性值，如果属性不存在或发生错误则返回 null
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeGetter(Object obj, String propertyName) {
        Object result = obj;
        // 分割多级属性
        String[] properties = propertyName.split("\\.");

        for (String property : properties) {
            // 构造 getter 方法名
            String getterMethodName = GETTER_PREFIX + capitalize(property);

            // 调用当前对象的 getter 方法
            result = invoke(result, getterMethodName);
            // 如果返回的对象是 null，则不继续调用，直接返回 null
            if (result == null) {
                return null;
            }
        }
        return (E) result;
    }

    /**
     * 使用反射调用方法
     *
     * @param obj        目标对象
     * @param methodName 方法名
     * @return 方法返回的结果，如果对象为 null 或方法调用失败，返回 null
     */
    @SuppressWarnings("all")
    private static Object invoke(Object obj, String methodName) {
        if (obj == null) {
            return null;
        }

        try {
            // 获取类的字节码对象
            Class<?> clazz = obj.getClass();
            // 获取方法对象，避免每次都通过反射查找方法
            Method method = clazz.getMethod(methodName);
            // 调用方法并返回结果
            return method.invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            // 如果出现异常，返回 null
            return null;
        }
    }

    /**
     * 将首字母大写的工具方法
     *
     * @param str 输入字符串
     * @return 首字母大写的字符串
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
