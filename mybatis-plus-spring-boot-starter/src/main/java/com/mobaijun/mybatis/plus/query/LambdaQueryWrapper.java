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
package com.mobaijun.mybatis.plus.query;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.springframework.util.ObjectUtils;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: LambdaQueryWrapper
 * 类描述：增加了一些简单条件的 IfPresent 条件 支持，Collection String Object 等等判断是否为空，或者是否为null
 *
 * @author MoBaiJun 2022/5/7 16:13
 */
public class LambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, LambdaQueryWrapper<T>>
        implements Query<LambdaQueryWrapper<T>, T, SFunction<T, ?>> {
    /**
     * 查询字段
     */
    private SharedString sqlSelect = new SharedString();

    /**
     * 不建议直接 new 该实例，使用 WrappersX.lambdaQueryX(entity)
     */
    public LambdaQueryWrapper() {
        this((T) null);
    }

    /**
     * 不建议直接 new 该实例，使用 WrappersX.lambdaQueryX(entity)
     *
     * @param entity entity
     */
    public LambdaQueryWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity)
     *
     * @param entityClass entityClass
     */
    public LambdaQueryWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(...)
     */
    LambdaQueryWrapper(T entity, Class<T> entityClass, SharedString sqlSelect, AtomicInteger paramNameSeq,
                       Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString lastSql,
                       SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.sqlSelect = sqlSelect;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    /**
     * SELECT 部分 SQL 设置
     *
     * @param columns 查询字段
     */
    @SafeVarargs
    @Override
    public final LambdaQueryWrapper<T> select(SFunction<T, ?>... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(columnsToString(false, columns));
        }
        return typedThis;
    }

    @Override
    public LambdaQueryWrapper<T> select(boolean condition, List<SFunction<T, ?>> columns) {
        return null;
    }


    @Override
    public LambdaQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        if (entityClass == null) {
            entityClass = getEntityClass();
        } else {
            setEntityClass(entityClass);
        }
        Assert.notNull(entityClass, "entityClass can not be null");
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
        return typedThis;
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }

    /**
     * 用于生成嵌套 sql
     * <p>
     * 故 sqlSelect 不向下传递
     * </p>
     */
    @Override
    protected LambdaQueryWrapper<T> instance() {
        return new LambdaQueryWrapper<>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs,
                new MergeSegments(), SharedString.emptyString(), SharedString.emptyString(),
                SharedString.emptyString());
    }

    @Override
    public void clear() {
        super.clear();
        sqlSelect.toNull();
    }

    // ======= 分界线，以上 copy 自 mybatis-plus 源码 =====

    /**
     * 当前条件只是否非null，且不为空
     *
     * @param obj 值
     * @return boolean 不为空返回true
     */
    @SuppressWarnings("rawtypes")
    private boolean isPresent(Object obj) {
        if (null == obj) {
            return false;
        } else if (obj instanceof CharSequence) {
            // 字符串比较特殊，如果是空字符串也不行
            return StringUtils.isNotBlank((CharSequence) obj);
        } else if (obj instanceof Collection) {
            return CollectionUtils.isNotEmpty((Collection) obj);
        }
        if (obj.getClass().isArray()) {
            return !ObjectUtils.isEmpty(obj);
        }
        return true;
    }

    public LambdaQueryWrapper<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        return super.eq(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> neIfPresent(SFunction<T, ?> column, Object val) {
        return super.ne(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        return super.gt(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> geIfPresent(SFunction<T, ?> column, Object val) {
        return super.ge(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        return super.lt(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> leIfPresent(SFunction<T, ?> column, Object val) {
        return super.le(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> likeIfPresent(SFunction<T, ?> column, Object val) {
        return super.like(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> notLikeIfPresent(SFunction<T, ?> column, Object val) {
        return super.notLike(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> likeLeftIfPresent(SFunction<T, ?> column, Object val) {
        return super.likeLeft(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> likeRightIfPresent(SFunction<T, ?> column, Object val) {
        return super.likeRight(isPresent(val), column, val);
    }

    public LambdaQueryWrapper<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        return super.in(isPresent(values), column, values);
    }

    public LambdaQueryWrapper<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        return super.in(isPresent(values), column, values);
    }

    public LambdaQueryWrapper<T> notInIfPresent(SFunction<T, ?> column, Object... values) {
        return super.notIn(isPresent(values), column, values);
    }

    public LambdaQueryWrapper<T> notInIfPresent(SFunction<T, ?> column, Collection<?> values) {
        return super.notIn(isPresent(values), column, values);
    }
}