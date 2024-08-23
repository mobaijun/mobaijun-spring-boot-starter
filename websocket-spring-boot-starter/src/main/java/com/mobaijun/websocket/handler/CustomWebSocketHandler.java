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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobaijun.websocket.exception.ErrorJsonMessageException;
import com.mobaijun.websocket.hodler.JsonMessageHandlerHolder;
import com.mobaijun.websocket.message.JsonWebSocketMessage;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Description: 自定义 WebSocket 消息处理器
 * Author: [mobaijun]
 * Date: [2024/8/23 9:27]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {

    /**
     * 消息解析器，用于解析 JSON 消息
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 启用对未转义控制字符的处理，避免解析 JSON 时因转义问题抛出异常
        MAPPER.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
    }

    /**
     * 普通文本消息处理器，如果解析 JSON 失败，则使用该处理器处理
     */
    private PlanTextMessageHandler planTextMessageHandler;

    /**
     * 构造函数注入 PlanTextMessageHandler 实例
     */
    public CustomWebSocketHandler() {
    }

    /**
     * 构造函数注入 PlanTextMessageHandler 实例
     *
     * @param planTextMessageHandler 普通文本消息处理器
     */
    public CustomWebSocketHandler(PlanTextMessageHandler planTextMessageHandler) {
        this.planTextMessageHandler = planTextMessageHandler;
    }

    /**
     * 处理 WebSocket 文本消息
     * <p>
     * 根据消息的内容决定是进行 JSON 处理还是回退到普通文本处理。
     * 如果 JSON 处理失败，将使用 PlanTextMessageHandler 处理普通文本消息。
     *
     * @param session 当前 WebSocket 会话
     * @param message 接收到的文本消息
     */
    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        // 空消息不处理
        if (message.getPayloadLength() == 0) {
            return;
        }

        // 获取消息载荷
        String payload = message.getPayload();
        try {
            // 尝试使用 JSON 处理
            handleWithJson(session, payload);
        } catch (ErrorJsonMessageException ex) {
            log.debug("消息载荷 [{}] 回退使用 PlanTextMessageHandler，原因：{}", payload, ex.getMessage());
            // 处理 JSON 异常后，使用 PlanTextMessageHandler 处理普通文本消息
            if (this.planTextMessageHandler != null) {
                this.planTextMessageHandler.handle(session, payload);
            } else {
                log.error("[handleTextMessage] 普通文本消息（{}）没有对应的消息处理器", payload);
            }
        }
    }

    /**
     * 使用 JSON 处理消息
     * <p>
     * 解析消息载荷，检查其格式，并调用相应的消息处理器。
     *
     * @param session 当前 WebSocket 会话
     * @param payload 消息载荷
     */
    private void handleWithJson(WebSocketSession session, String payload) {
        // 消息类型必有一个属性 type，先解析以获取该属性
        JsonNode jsonNode;
        try {
            jsonNode = MAPPER.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new ErrorJsonMessageException("json 解析异常");
        }

        // 确保消息类型是 object 类型
        if (!jsonNode.isObject()) {
            throw new ErrorJsonMessageException("json 格式异常！非 object 类型！");
        }

        JsonNode typeNode = jsonNode.get(JsonWebSocketMessage.TYPE_FIELD);
        String messageType = typeNode.asText();
        if (messageType == null) {
            throw new ErrorJsonMessageException("json 无 type 属性");
        }

        // 根据消息类型获取对应的消息处理器
        JsonMessageHandler<JsonWebSocketMessage> jsonMessageHandler = JsonMessageHandlerHolder.getHandler(messageType);
        if (jsonMessageHandler == null) {
            log.error("[handleTextMessage] 消息类型（{}）不存在对应的消息处理器", messageType);
            return;
        }

        // 处理消息
        Class<? extends JsonWebSocketMessage> messageClass = jsonMessageHandler.getMessageClass();
        JsonWebSocketMessage websocketMessageJson;
        try {
            websocketMessageJson = MAPPER.treeToValue(jsonNode, messageClass);
        } catch (JsonProcessingException e) {
            throw new ErrorJsonMessageException("消息序列化异常，class " + messageClass);
        }
        jsonMessageHandler.handle(session, websocketMessageJson);
    }
}
