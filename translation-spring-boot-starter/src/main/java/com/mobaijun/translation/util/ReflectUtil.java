/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.translation.util;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
     * 方法缓存，避免重复反射查找，提升性能
     * Key: 类名 + 方法名，Value: Method 对象
     */
    private static final ConcurrentMap<String, Method> METHOD_CACHE = new ConcurrentHashMap<>();

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
        if (obj == null || propertyName == null || propertyName.isEmpty()) {
            return null;
        }

        Object result = obj;
        // 分割多级属性
        String[] properties = propertyName.split("\\.");

        for (String property : properties) {
            if (property == null || property.isEmpty()) {
                continue;
            }
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
     * 使用反射调用方法，带方法缓存优化
     *
     * @param obj        目标对象
     * @param methodName 方法名
     * @return 方法返回的结果，如果对象为 null 或方法调用失败，返回 null
     */
    @SuppressWarnings("all")
    private static Object invoke(Object obj, String methodName) {
        if (obj == null || methodName == null || methodName.isEmpty()) {
            return null;
        }

        try {
            // 获取类的字节码对象
            Class<?> clazz = obj.getClass();
            // 构造缓存键
            String cacheKey = clazz.getName() + "#" + methodName;
            
            // 从缓存中获取方法，如果不存在则查找并缓存
            Method method = METHOD_CACHE.computeIfAbsent(cacheKey, key -> {
                try {
                    return clazz.getMethod(methodName);
                } catch (NoSuchMethodException e) {
                    return null;
                }
            });

            // 如果方法不存在，返回 null
            if (method == null) {
                return null;
            }

            // 调用方法并返回结果
            return method.invoke(obj);
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
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

    /**
     * 清空方法缓存（主要用于测试或内存优化场景）
     */
    public static void clearCache() {
        METHOD_CACHE.clear();
    }

    /**
     * 获取当前缓存的方法数量
     *
     * @return 缓存的方法数量
     */
    public static int getCacheSize() {
        return METHOD_CACHE.size();
    }
}