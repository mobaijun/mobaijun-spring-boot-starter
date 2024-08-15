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
package com.mobaijun.redisson.manager;

import com.mobaijun.core.spring.SpringUtil;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import java.util.concurrent.Callable;
import org.springframework.cache.Cache;

/**
 * Description: [
 * Caffeine缓存装饰器，用于装饰并增强现有的缓存实现，通过集成Caffeine缓存库提供额外的缓存层。
 * <p>
 * 该装饰器允许在现有的缓存实现之上添加一层Caffeine缓存，以提高缓存的性能和并发处理能力。
 * 通过装饰器模式，可以在不修改现有缓存代码的基础上，增加新的缓存功能和特性。
 * </p>
 * ]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NonNullApi
public class CaffeineCacheDecorator implements Cache {

    /**
     * 缓存实例。
     */
    private static final com.github.benmanes.caffeine.cache.Cache<Object, Object>
            CAFFEINE = SpringUtil.getBean("caffeine");

    /**
     * 缓存实例。
     */
    private final Cache cache;

    /**
     * 创建一个新的CaffeineCacheDecorator实例。
     *
     * @param cache 被装饰的缓存实例。
     */
    public CaffeineCacheDecorator(Cache cache) {
        this.cache = cache;
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return cache.getNativeCache();
    }

    /**
     * 获取缓存项的唯一键。
     *
     * @param key 缓存项的键。
     * @return 缓存项的唯一键。
     */
    public String getUniqueKey(Object key) {
        return cache.getName() + ":" + key;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object o = CAFFEINE.get(getUniqueKey(key), k -> cache.get(key));
        return (ValueWrapper) o;
    }

    /**
     * 从缓存中获取一个对象，允许指定返回类型。
     *
     * @param key  缓存项的键。
     * @param type 期望的返回类型。
     * @param <T>  泛型类型。
     * @return 缓存中的对象，如果指定了类型，则返回该类型的对象。
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, @Nullable Class<T> type) {
        Object o = CAFFEINE.get(getUniqueKey(key), k -> cache.get(key, type));
        return (T) o;
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        CAFFEINE.invalidate(getUniqueKey(key));
        cache.put(key, value);
    }

    /**
     * 只有在键不存在时，才将键值对放入缓存。
     *
     * @param key   缓存项的键。
     * @param value 缓存项的值。
     * @return 之前的值，如果不存在则返回null。
     */
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        CAFFEINE.invalidate(getUniqueKey(key));
        return cache.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        evictIfPresent(key);
    }

    /**
     * 如果缓存中存在该键，则删除它。
     *
     * @param key 缓存项的键。
     * @return 如果缓存中存在该键，则返回true。
     */
    public boolean evictIfPresent(Object key) {
        boolean b = cache.evictIfPresent(key);
        if (b) {
            CAFFEINE.invalidate(getUniqueKey(key));
        }
        return b;
    }

    /**
     * 清除缓存中的所有项。
     */
    @Override
    public void clear() {
        cache.clear();
    }

    /**
     * 清除缓存中的所有项，并返回操作是否成功。
     *
     * @return 如果缓存被成功清除，则返回true。
     */
    public boolean invalidate() {
        return cache.invalidate();
    }

    /**
     * 获取缓存
     *
     * @param key         缓存key
     * @param valueLoader 缓存加载器
     * @param <T>         泛型
     * @return 缓存对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object o = CAFFEINE.get(getUniqueKey(key), k -> cache.get(key, valueLoader));
        return (T) o;
    }
}
