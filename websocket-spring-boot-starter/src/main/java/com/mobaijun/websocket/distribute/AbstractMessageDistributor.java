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

import com.mobaijun.websocket.dto.MessageDTO;
import com.mobaijun.websocket.message.WebSocketMessageSender;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

/**
 * Description:
 * 一个用于分发消息到 WebSocket 会话的抽象基类。
 * <p>
 * 这个类为实现特定的消息分发策略提供了基础，通过封装与 WebSocket 会话管理
 * 相关的通用功能，帮助简化具体实现。
 * Author: [mobaijun]
 * Date: [2024/8/23 9:25]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public abstract class AbstractMessageDistributor implements MessageDistributor {

    /**
     * WebSocket 会话存储器。
     * 这个实例用于在消息分发过程中管理和访问 WebSocket 会话。
     */
    private final WebSocketSessionStore webSocketSessionStore;

    /**
     * 构造一个 {@code AbstractMessageDistributor} 实例，并指定
     * {@link WebSocketSessionStore}。
     *
     * @param webSocketSessionStore 用于管理 WebSocket 会话的 {@link WebSocketSessionStore} 实例。
     *                              不能为空。
     * @throws IllegalArgumentException 如果 {@code webSocketSessionStore} 为 {@code null}。
     */
    protected AbstractMessageDistributor(WebSocketSessionStore webSocketSessionStore) {
        if (webSocketSessionStore == null) {
            throw new IllegalArgumentException("webSocketSessionStore 不能为空");
        }
        this.webSocketSessionStore = webSocketSessionStore;
    }

    /**
     * 对当前服务中的 websocket 连接做消息推送
     *
     * @param messageDO 消息实体
     */
    protected void doSend(MessageDTO messageDO) {
        // 是否广播发送
        Boolean needBroadcast = messageDO.getNeedBroadcast();

        // 获取待发送的 sessionKeys
        Collection<Object> sessionKeys;
        if (needBroadcast != null && needBroadcast) {
            sessionKeys = this.webSocketSessionStore.getSessionKeys();
        } else {
            sessionKeys = messageDO.getSessionKeys();
        }
        if (CollectionUtils.isEmpty(sessionKeys)) {
            log.warn("发送 websocket 消息，却没有找到对应 sessionKeys, messageDo: {}", messageDO);
            return;
        }

        String messageText = messageDO.getMessageText();
        Boolean onlyOneClientInSameKey = messageDO.getOnlyOneClientInSameKey();
        // 遍历 sessionKeys，发送消息
        for (Object sessionKey : sessionKeys) {
            Collection<WebSocketSession> sessions = this.webSocketSessionStore.getSessions(sessionKey);
            if (!CollectionUtils.isEmpty(sessions)) {
                // 相同 sessionKey 的客户端只推送一次操作
                if (onlyOneClientInSameKey != null && onlyOneClientInSameKey) {
                    WebSocketSession wsSession = sessions.iterator().next();
                    WebSocketMessageSender.send(wsSession, messageText);
                    continue;
                }
                for (WebSocketSession wsSession : sessions) {
                    WebSocketMessageSender.send(wsSession, messageText);
                }
            }
        }
    }
}
