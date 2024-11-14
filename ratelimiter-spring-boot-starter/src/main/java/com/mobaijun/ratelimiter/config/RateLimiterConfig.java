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
import java.io.Serializable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    /**
     * 配置 Redis 连接工厂，提供给 RedisTemplate 使用。
     * <p>
     * 如果没有其他 Redis 连接工厂的 Bean，Spring 会创建一个 LettuceConnectionFactory，
     * 并根据应用程序的配置文件中的 Redis 设置（如 host, port, password 等）进行初始化。
     * </p>
     *
     * @param properties 从配置文件加载的 RateLimiterProperties 类（包含 Redis 配置信息）
     * @return 返回一个 RedisConnectionFactory 实例，用于连接 Redis 服务
     */
    @Bean
    // 如果没有 RedisConnectionFactory Bean，则创建此 Bean
    @ConditionalOnMissingBean
    public LettuceConnectionFactory redisConnectionFactory(RateLimiterProperties properties) {
        // 创建 Redis 配置对象并设置主机、端口和密码
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
        // 设置密码
        redisStandaloneConfiguration.setPassword(properties.getPassword());
        // 返回连接工厂
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * 配置 RedisTemplate，用于与 Redis 进行交互。
     * <p>
     * 如果没有定义名为 "intRedisTemplate" 的 RedisTemplate，Spring 会创建一个新的 RedisTemplate，
     * 并配置使用 Lettuce 连接工厂以及 JSON 序列化与字符串序列化。
     * </p>
     *
     * @param redisConnectionFactory Redis 连接工厂，提供与 Redis 服务的连接
     * @return 返回 RedisTemplate 实例，用于执行 Redis 操作
     */
    @Bean
    @ConditionalOnMissingBean(name = "intRedisTemplate")
    public RedisTemplate<String, Serializable> intRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建 RedisTemplate 实例
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();

        // 配置 RedisTemplate 的 key 和 value 序列化方式
        // 设置 key 的序列化方式为字符串
        template.setKeySerializer(new StringRedisSerializer());
        // 设置 value 的序列化方式为 JSON
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 返回配置好的 RedisTemplate
        return template;
    }
}