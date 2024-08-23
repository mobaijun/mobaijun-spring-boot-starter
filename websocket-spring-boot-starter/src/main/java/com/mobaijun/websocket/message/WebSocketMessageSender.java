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
package com.mobaijun.websocket.message;

import com.mobaijun.json.utils.JsonUtil;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Description: 发送消息
 * Author: [mobaijun]
 * Date: [2024/8/23 9:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public final class WebSocketMessageSender {

    private WebSocketMessageSender() {
    }

    /**
     * 发送消息
     *
     * @param session 客户端
     * @param message 消息
     */
    public static void send(WebSocketSession session, JsonWebSocketMessage message) {
        send(session, JsonUtil.toJsonString(message));
    }

    /**
     * 发送消息
     *
     * @param session 客户端
     * @param message 消息
     * @return 发送成功返回 true
     */
    public static boolean send(WebSocketSession session, String message) {
        if (session == null) {
            log.error("[send] session 为 null");
            return false;
        }
        if (!session.isOpen()) {
            log.error("[send] session 已经关闭");
            return false;
        }
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("[send] session({}) 发送消息({}) 异常", session, message, e);
            return false;
        }
        return true;
    }
}
