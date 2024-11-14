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
package com.mobaijun.ratelimiter.config;

import com.mobaijun.ratelimiter.aspect.RateLimiterAspect;
import com.mobaijun.ratelimiter.properties.RateLimiterProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RateLimiterConfig 配置类，负责配置限流相关的组件。
 * <p>
 * 该配置类包含了 Redis 连接工厂、RedisTemplate 和 RateLimiterAspect 的配置，
 * 并且使用了 RedisAutoConfiguration 自动配置 Redis 连接。
 * </p>
 */
@Configuration
// 在 RedisAutoConfiguration 完成后进行配置
@AutoConfigureAfter(RedisAutoConfiguration.class)
// 启用 RateLimiterProperties 配置类的支持
@EnableConfigurationProperties(RateLimiterProperties.class)
public class RateLimiterConfig {

    /**
     * 配置限流的切面（Aspect），用于在方法执行前拦截并执行限流逻辑。
     *
     * @return 返回 RateLimiterAspect 的实例，作为切面来拦截限流操作
     */
    @Bean
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect();
    }
}