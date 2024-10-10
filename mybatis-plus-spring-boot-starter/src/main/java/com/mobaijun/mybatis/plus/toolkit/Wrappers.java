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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mobaijun.mybatis.plus.query.LambdaAliasQueryWrapper;
import com.mobaijun.mybatis.plus.query.LambdaQueryWrapper;
import java.io.Serial;
import java.util.Collections;
import java.util.Map;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Wrappers {@link com.baomidou.mybatisplus.core.toolkit.Wrappers}
 * 类描述：包装类
 *
 * @author MoBaiJun 2022/5/7 16:13
 */
public final class Wrappers {

    /**
     * 空的 EmptyWrapper
     */
    private static final QueryWrapper<?> emptyWrapper = new Wrappers.EmptyWrapper<>();


    private Wrappers() {
        // ignore
    }

    /**
     * 获取 QueryWrapper&lt;T&gt;
     *
     * @param <T> 实体类泛型
     * @return QueryWrapper&lt;T&gt;
     */
    public static <T> QueryWrapper<T> query() {
        return new QueryWrapper<>();
    }

    /**
     * 获取 QueryWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return QueryWrapper&lt;T&gt;
     */
    public static <T> QueryWrapper<T> query(T entity) {
        return new QueryWrapper<>(entity);
    }

    /**
     * 获取 QueryWrapper&lt;T&gt;
     *
     * @param entityClass 实体类class
     * @param <T>         实体类泛型
     * @return QueryWrapper&lt;T&gt;
     */
    public static <T> QueryWrapper<T> query(Class<T> entityClass) {
        return new QueryWrapper<>(entityClass);
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
     * 获取 UpdateWrapper&lt;T&gt;
     *
     * @param <T> 实体类泛型
     * @return UpdateWrapper&lt;T&gt;
     */
    public static <T> UpdateWrapper<T> update() {
        return new UpdateWrapper<>();
    }

    /**
     * 获取 UpdateWrapper&lt;T&gt;
     *
     * @param entity 实体类
     * @param <T>    实体类泛型
     * @return UpdateWrapper&lt;T&gt;
     */
    public static <T> UpdateWrapper<T> update(T entity) {
        return new UpdateWrapper<>(entity);
    }

    /**
     * 获取 LambdaUpdateWrapper&lt;T&gt;
     *
     * @param <T> 实体类泛型
     * @return LambdaUpdateWrapper&lt;T&gt;
     */
    public static <T> LambdaUpdateWrapper<T> lambdaUpdate() {
        return new LambdaUpdateWrapper<>();
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

    /**
     * 一个空的QueryWrapper子类该类不包含任何条件
     *
     * @param <T>
     * @see com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     */
    private static class EmptyWrapper<T> extends QueryWrapper<T> {

        @Serial
        private static final long serialVersionUID = -2515957613998092272L;

        @Override
        public T getEntity() {
            return null;
        }

        @Override
        public Wrappers.EmptyWrapper<T> setEntity(T entity) {
            throw new UnsupportedOperationException();
        }

        @Override
        public QueryWrapper<T> setEntityClass(Class<T> entityClass) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Class<T> getEntityClass() {
            return null;
        }

        @Override
        public String getSqlSelect() {
            return null;
        }

        @Override
        public MergeSegments getExpression() {
            return null;
        }

        @Override
        public boolean isEmptyOfWhere() {
            return true;
        }

        @Override
        public boolean isEmptyOfNormal() {
            return true;
        }

        @Override
        public boolean isNonEmptyOfEntity() {
            return !isEmptyOfEntity();
        }

        @Override
        public boolean isEmptyOfEntity() {
            return true;
        }

        @Override
        protected void initNeed() {
        }

        @Override
        public Wrappers.EmptyWrapper<T> last(boolean condition, String lastSql) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getSqlSegment() {
            return null;
        }

        @Override
        public Map<String, Object> getParamNameValuePairs() {
            return Collections.emptyMap();
        }

        @Override
        protected String columnsToString(String... columns) {
            return null;
        }

        @Override
        protected String columnToString(String column) {
            return null;
        }

        @Override
        protected Wrappers.EmptyWrapper<T> instance() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }
}