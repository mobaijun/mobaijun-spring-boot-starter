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
import com.mobaijun.websocket.session.WebSocketSessionStore;

/**
 * Description:
 * 本地消息分发器，继承自 {@link AbstractMessageDistributor}。
 * <p>
 * 该类负责将消息分发到 WebSocket 会话。具体的实现可以扩展
 * {@code AbstractMessageDistributor} 类并实现消息分发逻辑。
 * </p>
 * Author: [mobaijun]
 * <p>
 * Date: [2024/8/23 9:25]
 * <p>
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class LocalMessageDistributor extends AbstractMessageDistributor {

    /**
     * 构造一个 {@code LocalMessageDistributor} 实例。
     *
     * @param webSocketSessionStore 用于管理 WebSocket 会话的 {@link WebSocketSessionStore} 实例。
     *                              不能为空。
     */
    public LocalMessageDistributor(WebSocketSessionStore webSocketSessionStore) {
        super(webSocketSessionStore);
    }

    /**
     * 分发消息到 WebSocket 会话。
     * <p>
     * 该方法接收一个消息对象，并将其发送到相应的 WebSocket 会话。
     * </p>
     *
     * @param messageDO 需要发送的消息对象。
     */
    @Override
    public void distribute(MessageDTO messageDO) {
        doSend(messageDO);
    }
}
