package com.mobaijun.mybatis.plus.toolkit;

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
}
