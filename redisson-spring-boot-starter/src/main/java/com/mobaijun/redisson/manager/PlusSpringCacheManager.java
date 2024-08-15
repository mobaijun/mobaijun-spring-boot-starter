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

import com.mobaijun.redisson.util.RedisUtil;
import io.micrometer.common.lang.NonNullApi;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.Setter;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonCache;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.util.StringUtils;

/**
 * Description: [
 * A {@link org.springframework.cache.CacheManager} implementation
 * backed by Redisson instance.
 * <p>
 * 修改 RedissonSpringCacheManager 源码
 * 重写 cacheName 处理方法 支持多参数
 * ]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NonNullApi
@SuppressWarnings("unchecked")
public class PlusSpringCacheManager implements CacheManager {

    /**
     * 缓存配置
     */
    Map<String, CacheConfig> configMap = new ConcurrentHashMap<>();

    /**
     * 缓存实例
     */
    ConcurrentMap<String, Cache> instanceMap = new ConcurrentHashMap<>();

    /**
     * 动态开启
     */
    private boolean dynamic = true;

    /**
     * -- SETTER --
     * Defines possibility of storing
     * values.
     * <p>
     * Default is <code>true</code>
     */
    @Setter
    private boolean allowNullValues = true;

    /**
     * -- SETTER --
     * Defines if cache aware of Spring-managed transactions.
     * If
     * put/evict operations are executed only for successful transaction in after-commit phase.
     * <p>
     * Default is <code>false</code>
     */
    @Setter
    private boolean transactionAware = true;

    /**
     * Creates CacheManager supplied by Redisson instance
     */
    public PlusSpringCacheManager() {
    }

    /**
     * Set cache config mapped by cache name
     *
     * @param config object
     */
    public void setConfig(Map<String, ? extends CacheConfig> config) {
        this.configMap = (Map<String, CacheConfig>) config;
    }

    protected CacheConfig createDefaultConfig() {
        return new CacheConfig();
    }

    @Override
    public Cache getCache(String name) {
        // 重写 cacheName 支持多参数
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];

        Cache cache = instanceMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic) {
            return cache;
        }

        CacheConfig config = configMap.get(name);
        if (config == null) {
            config = createDefaultConfig();
            configMap.put(name, config);
        }

        if (array.length > 1) {
            config.setTTL(DurationStyle.detectAndParse(array[1]).toMillis());
        }
        if (array.length > 2) {
            config.setMaxIdleTime(DurationStyle.detectAndParse(array[2]).toMillis());
        }
        if (array.length > 3) {
            config.setMaxSize(Integer.parseInt(array[3]));
        }

        if (config.getMaxIdleTime() == 0 && config.getTTL() == 0 && config.getMaxSize() == 0) {
            return createMap(name, config);
        }

        return createMapCache(name, config);
    }

    /**
     * 创建map缓存
     *
     * @param name   缓存名称
     * @param config 缓存配置
     * @return 缓存实例
     */
    private Cache createMap(String name, CacheConfig config) {
        RMap<Object, Object> map = RedisUtil.getClient().getMap(name);

        Cache cache = new CaffeineCacheDecorator(new RedissonCache(map, allowNullValues));
        if (transactionAware) {
            cache = new TransactionAwareCacheDecorator(cache);
        }
        Cache oldCache = instanceMap.putIfAbsent(name, cache);
        if (oldCache != null) {
            cache = oldCache;
        }
        return cache;
    }

    /**
     * 创建map缓存
     *
     * @param name   缓存名称
     * @param config 缓存配置
     * @return 缓存实例
     */
    private Cache createMapCache(String name, CacheConfig config) {
        RMapCache<Object, Object> map = RedisUtil.getClient().getMapCache(name);

        Cache cache = new CaffeineCacheDecorator(new RedissonCache(map, config, allowNullValues));
        if (transactionAware) {
            cache = new TransactionAwareCacheDecorator(cache);
        }
        Cache oldCache = instanceMap.putIfAbsent(name, cache);
        if (oldCache != null) {
            cache = oldCache;
        } else {
            map.setMaxSize(config.getMaxSize());
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(configMap.keySet());
    }

    /**
     * Defines 'fixed' cache names.
     * A new cache instance will not be created in dynamic for non-defined names.
     * <p>
     * `null` parameter setups dynamic mode
     *
     * @param names of caches
     */
    public void setCacheNames(Collection<String> names) {
        for (String name : names) {
            getCache(name);
        }
        dynamic = false;
    }
}
