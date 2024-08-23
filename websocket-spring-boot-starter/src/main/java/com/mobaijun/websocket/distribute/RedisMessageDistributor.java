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

import com.mobaijun.json.utils.JsonUtil;
import com.mobaijun.websocket.dto.MessageDTO;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Description: Redis 消息分发器
 * Author: [mobaijun]
 * Date: [2024/8/23 9:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class RedisMessageDistributor extends AbstractMessageDistributor implements MessageListener {

    /**
     * Redis 频道名称
     */
    public static final String CHANNEL = "websocket-send";

    /**
     * StringRedisTemplate 实例，用于操作 Redis
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造函数
     *
     * @param webSocketSessionStore WebSocket 会话存储，用于管理会话
     * @param stringRedisTemplate   Redis 操作模板，用于发送消息
     */
    public RedisMessageDistributor(WebSocketSessionStore webSocketSessionStore,
                                   StringRedisTemplate stringRedisTemplate) {
        super(webSocketSessionStore);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 消息分发
     * <p>
     * 将消息对象序列化为 JSON 字符串，并通过 Redis 频道进行分发。
     *
     * @param messageDO 发送的消息对象
     */
    @Override
    public void distribute(MessageDTO messageDO) {
        // 将消息对象转换为 JSON 字符串
        String str = JsonUtil.toJsonString(messageDO);
        assert str != null; // 确保转换结果不为空
        // 通过 Redis 频道将消息发送出去
        this.stringRedisTemplate.convertAndSend(CHANNEL, str);
    }

    /**
     * 处理从 Redis 频道接收到的消息
     * <p>
     * 当接收到 Redis 频道的消息时，进行处理。
     *
     * @param message Redis 消息对象
     * @param bytes   消息体的字节数组
     */
    @Override
    public void onMessage(@NonNull Message message, byte[] bytes) {
        log.info("redis channel Listener message send {}", message);

        // 反序列化 Redis 频道名称
        byte[] channelBytes = message.getChannel();
        RedisSerializer<String> stringSerializer = this.stringRedisTemplate.getStringSerializer();
        String channel = stringSerializer.deserialize(channelBytes);

        // 仅处理匹配的频道
        if (CHANNEL.equals(channel)) {
            // 反序列化消息体
            byte[] bodyBytes = message.getBody();
            String body = stringSerializer.deserialize(bodyBytes);
            MessageDTO messageDO = JsonUtil.parseObject(body, MessageDTO.class);
            assert messageDO != null; // 确保消息对象不为空
            // 执行实际的消息发送逻辑
            doSend(messageDO);
        }
    }
}
