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
package com.mobaijun.core.util.reflect;

import com.mobaijun.common.text.StringUtil;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.util.StringUtils;

/**
 * Description: [反射工具类. 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.]
 * Author: [mobaijun]
 * Date: [2024/8/12 9:55]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class ReflectUtil extends cn.hutool.core.util.ReflectUtil {

    private static final String SETTER_PREFIX = "set";

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
        Object object = obj;
        for (String name : StringUtil.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invoke(object, getterMethodName);
        }
        return (E) object;
    }


    /**
     * 调用对象的 Setter 方法。
     * 支持多级属性设置，例如：对象名.属性名.属性名
     *
     * @param obj          目标对象
     * @param propertyName 属性名，支持点号分隔的多级属性
     * @param value        要设置的值
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value) {
        Object object = obj;
        String[] names = StringUtil.split(propertyName, ".");
        for (int i = 0; i < Objects.requireNonNull(names).length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invoke(object, getterMethodName);
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                Method method = getMethodByName(object.getClass(), setterMethodName);
                invoke(object, method, value);
            }
        }
    }
}
