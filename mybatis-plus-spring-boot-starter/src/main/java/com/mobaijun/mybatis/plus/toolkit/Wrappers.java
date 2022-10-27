/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.mybatis.plus.toolkit;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mobaijun.mybatis.plus.query.LambdaAliasQueryWrapper;
import com.mobaijun.mybatis.plus.query.LambdaQueryWrapper;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Wrappers
 * 类描述：包装类
 *
 * @author MoBaiJun 2022/5/7 16:13
 */
public final class Wrappers {

    private Wrappers() {
    }

    /**
     * 获取 LambdaQueryWrapper&lt;T&gt;
     *
     * @param <T> 实体类泛型
     * @return LambdaQueryWrapper&lt;T&gt;
     */
    public static <T> LambdaQueryWrapper<T> lambdaQuery() {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 获取 LambdaQueryWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return LambdaQueryWrapper&lt;T&gt;
     */
    public static <T> LambdaQueryWrapper<T> lambdaQuery(T entity) {
        return new LambdaQueryWrapper<>(entity);
    }

    /**
     * 获取 LambdaQueryWrapper&lt;T&gt;
     *
     * @param entityClass 实体类class
     * @param <T>         实体类泛型
     * @return LambdaQueryWrapper&lt;T&gt;
     * @since 3.3.1
     */
    public static <T> LambdaQueryWrapper<T> lambdaQuery(Class<T> entityClass) {
        return new LambdaQueryWrapper<>(entityClass);
    }

    /**
     * 获取 LambdaAliasQueryWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return LambdaAliasQueryWrapper&lt;T&gt;
     */
    public static <T> LambdaAliasQueryWrapper<T> lambdaAliasQuery(T entity) {
        return new LambdaAliasQueryWrapper<>(entity);
    }

    /**
     * 获取 LambdaAliasQueryWrapper&lt;T&gt;
     *
     * @param entityClass 实体类class
     * @param <T>         实体类泛型
     * @return LambdaAliasQueryWrapper&lt;T&gt;
     * @since 3.3.1
     */
    public static <T> LambdaAliasQueryWrapper<T> lambdaAliasQuery(Class<T> entityClass) {
        return new LambdaAliasQueryWrapper<>(entityClass);
    }

    /**
     * 获取 LambdaUpdateWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return LambdaUpdateWrapper&lt;T&gt;
     */
    public static <T> LambdaUpdateWrapper<T> lambdaUpdate(T entity) {
        return new LambdaUpdateWrapper<>(entity);
    }

    /**
     * 获取 LambdaUpdateWrapper&lt;T&gt;
     *
     * @param entityClass 实体类class
     * @param <T>         实体类泛型
     * @return LambdaUpdateWrapper&lt;T&gt;
     * @since 3.3.1
     */
    public static <T> LambdaUpdateWrapper<T> lambdaUpdate(Class<T> entityClass) {
        return new LambdaUpdateWrapper<>(entityClass);
    }
}