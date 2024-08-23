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
package com.mobaijun.websocket.distribute;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Description: redis消息监听器初始化
 * Author: [mobaijun]
 * Date: [2024/8/23 9:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
public class RedisMessageListenerInitializer {

    /**
     * Redis 消息监听容器，用于管理和调度 Redis 消息的监听
     */
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    /**
     * Redis 消息分发器，用于处理接收到的 Redis 消息
     */
    private final RedisMessageDistributor redisWebsocketMessageListener;

    /**
     * 在类初始化后添加消息监听器
     * <p>
     * 使用 @PostConstruct 注解的方法将在类实例化并注入依赖之后调用。该方法将 Redis 消息分发器
     * 注册到 Redis 消息监听容器中，以便它能够监听指定的 Redis 频道并处理接收到的消息。
     */
    @PostConstruct
    public void addMessageListener() {
        // 将 Redis 消息分发器注册到 Redis 消息监听容器中，并指定监听的频道
        this.redisMessageListenerContainer.addMessageListener(
                this.redisWebsocketMessageListener,
                new PatternTopic(RedisMessageDistributor.CHANNEL)
        );
    }
}

