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
package com.mobaijun.websocket.configuration;

import com.mobaijun.websocket.constant.MessageDistributorTypeConstants;
import com.mobaijun.websocket.distribute.MessageDistributor;
import com.mobaijun.websocket.distribute.RedisMessageDistributor;
import com.mobaijun.websocket.distribute.RedisMessageListenerInitializer;
import com.mobaijun.websocket.properties.WebSocketProperties;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Description:
 * Redis 消息分发器配置类
 *
 * <p>当存在 {@link StringRedisTemplate} 类，并且配置属性中指定使用 Redis 作为消息分发器时，
 * 此配置类会配置基于 Redis 的消息分发器和相关的监听容器。
 * Author: [mobaijun]
 * Date: [2024/8/23 9:24]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(StringRedisTemplate.class)
@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "message-distributor",
        havingValue = MessageDistributorTypeConstants.REDIS)
public class RedisMessageDistributorConfig {

    /**
     * 配置的 WebSocket 会话存储器。
     */
    private final WebSocketSessionStore webSocketSessionStore;

    /**
     * 配置 Redis 消息分发器。
     *
     * <p>如果没有其他自定义的 {@link MessageDistributor} 实例，则会创建并返回
     * 基于 Redis 的 {@link RedisMessageDistributor}，用于在分布式环境中进行消息分发。
     *
     * @param stringRedisTemplate Redis 操作模板，依赖注入
     * @return 配置好的 Redis 消息分发器实例
     */
    @Bean
    @ConditionalOnMissingBean(MessageDistributor.class)
    public RedisMessageDistributor messageDistributor(StringRedisTemplate stringRedisTemplate) {
        return new RedisMessageDistributor(this.webSocketSessionStore, stringRedisTemplate);
    }

    /**
     * 配置 Redis 消息监听容器。
     *
     * <p>如果 {@link RedisMessageDistributor} 已经被定义，则配置并返回一个
     * {@link RedisMessageListenerContainer} 实例，用于监听 Redis 频道的消息。
     *
     * @param connectionFactory Redis 连接工厂，依赖注入
     * @return 配置好的 Redis 消息监听容器实例
     */
    @Bean
    @ConditionalOnBean(RedisMessageDistributor.class)
    @ConditionalOnMissingBean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    /**
     * 初始化 Redis 消息监听器。
     *
     * <p>配置并返回 {@link RedisMessageListenerInitializer} 实例，用于将 Redis 消息监听容器
     * 与消息分发器绑定，并开始监听 Redis 频道的消息。
     *
     * @param redisMessageListenerContainer Redis 消息监听容器
     * @param redisWebsocketMessageListener Redis 消息分发器，依赖注入
     * @return 配置好的 Redis 消息监听器初始化器
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisMessageListenerInitializer redisMessageListenerInitializer(
            RedisMessageListenerContainer redisMessageListenerContainer,
            RedisMessageDistributor redisWebsocketMessageListener) {
        return new RedisMessageListenerInitializer(redisMessageListenerContainer, redisWebsocketMessageListener);
    }
}
