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
package com.mobaijun.redisson.util;

import com.mobaijun.core.spring.SpringUtil;
import java.util.Objects;
import java.util.Set;
import org.redisson.api.RMap;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Description: [缓存操作工具类]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@SuppressWarnings(value = {"unchecked"})
public class CacheUtil {

    /**
     * 缓存管理器
     */
    private static final CacheManager CACHE_MANAGER = SpringUtil.getBean(CacheManager.class);

    /**
     * 获取缓存组内所有的KEY
     *
     * @param cacheNames 缓存组名称
     */
    public static <T> Set<T> keys(String cacheNames) {
        RMap<T, T> rmap = (RMap<T, T>)
                Objects.requireNonNull(CACHE_MANAGER.getCache(cacheNames)).getNativeCache();
        return rmap.keySet();
    }

    /**
     * 获取缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     */
    public static <T> T get(String cacheNames, Object key) {
        Cache.ValueWrapper wrapper = Objects.requireNonNull(CACHE_MANAGER.getCache(cacheNames)).get(key);
        return wrapper != null ? (T) wrapper.get() : null;
    }

    /**
     * 保存缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     * @param value      缓存值
     */
    public static void put(String cacheNames, Object key, Object value) {
        Objects.requireNonNull(CACHE_MANAGER.getCache(cacheNames)).put(key, value);
    }

    /**
     * 删除缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     */
    public static void evict(String cacheNames, Object key) {
        Objects.requireNonNull(CACHE_MANAGER.getCache(cacheNames)).evict(key);
    }

    /**
     * 清空缓存值
     *
     * @param cacheNames 缓存组名称
     */
    public static void clear(String cacheNames) {
        Objects.requireNonNull(CACHE_MANAGER.getCache(cacheNames)).clear();
    }
}
