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
package com.mobaijun.redis.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: RedisKeyGenerator
 * class description：配置 Redis key 生成策略
 *
 * @author MoBaiJun 2022/5/16 13:21
 */
public class RedisKeyGenerator implements CachingConfigurer {

    @Override
    public KeyGenerator keyGenerator() {
        return getKeyGenerator();
    }

    /**
     * 配置 Redis key 生成策略
     *
     * @return KeyGenerator
     */
    public static KeyGenerator getKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}