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
package com.mobaijun.core.util;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Description: [对象工具类]
 * Author: [mobaijun]
 * Date: [2024/12/27 11:11]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtil extends cn.hutool.core.util.ObjectUtil {

    /**
     * 如果对象不为空，则获取对象中的某个字段 ObjectUtils.notNullGetter(user, User::getName);
     *
     * @param obj  对象
     * @param func 获取方法
     * @return 对象字段
     */
    public static <T, E> E notNullGetter(T obj, Function<T, E> func) {
        if (isNotNull(obj) && isNotNull(func)) {
            return func.apply(obj);
        }
        return null;
    }

    /**
     * 如果对象不为空，则获取对象中的某个字段，否则返回默认值
     *
     * @param obj          对象
     * @param func         获取方法
     * @param defaultValue 默认值
     * @return 对象字段
     */
    public static <T, E> E notNullGetter(T obj, Function<T, E> func, E defaultValue) {
        if (isNotNull(obj) && isNotNull(func)) {
            return func.apply(obj);
        }
        return defaultValue;
    }

    /**
     * 如果值不为空，则返回值，否则返回默认值
     *
     * @param obj          对象
     * @param defaultValue 默认值
     * @return 对象字段
     */
    public static <T> T notNull(T obj, T defaultValue) {
        if (isNotNull(obj)) {
            return obj;
        }
        return defaultValue;
    }
}