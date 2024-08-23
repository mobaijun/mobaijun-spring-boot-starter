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
package com.mobaijun.websocket.handler;

import com.mobaijun.websocket.enums.WebSocketMessageTypeEnum;
import com.mobaijun.websocket.message.JsonWebSocketMessage;
import com.mobaijun.websocket.message.PingJsonWebSocketMessage;
import com.mobaijun.websocket.message.PongJsonWebSocketMessage;
import com.mobaijun.websocket.message.WebSocketMessageSender;
import org.springframework.web.socket.WebSocketSession;

/**
 * Description: 心跳json消息处理器
 * Author: [mobaijun]
 * Date: [2024/8/23 9:27]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class PingJsonMessageHandler implements JsonMessageHandler<PingJsonWebSocketMessage> {

    @Override
    public void handle(WebSocketSession session, PingJsonWebSocketMessage message) {
        JsonWebSocketMessage pongJsonWebSocketMessage = new PongJsonWebSocketMessage();
        WebSocketMessageSender.send(session, pongJsonWebSocketMessage);
    }

    @Override
    public String type() {
        return WebSocketMessageTypeEnum.PING.getValue();
    }

    @Override
    public Class<PingJsonWebSocketMessage> getMessageClass() {
        return PingJsonWebSocketMessage.class;
    }
}
