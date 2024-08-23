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
package com.mobaijun.websocket.hodler;

import com.mobaijun.websocket.handler.JsonMessageHandler;
import com.mobaijun.websocket.message.JsonWebSocketMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: json 消息处理器持有者
 * Author: [mobaijun]
 * Date: [2024/8/23 9:27]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public final class JsonMessageHandlerHolder {

    /**
     * 消息处理器 map
     */
    private static final Map<String, JsonMessageHandler<JsonWebSocketMessage>> MESSAGE_HANDLER_MAP = new ConcurrentHashMap<>();

    private JsonMessageHandlerHolder() {
    }

    /**
     * 获取消息处理器
     *
     * @param type 消息类型
     * @return 消息处理器
     */
    public static JsonMessageHandler<JsonWebSocketMessage> getHandler(String type) {
        return MESSAGE_HANDLER_MAP.get(type);
    }

    /**
     * 添加消息处理器
     *
     * @param jsonMessageHandler 消息处理器
     */
    public static void addHandler(JsonMessageHandler<JsonWebSocketMessage> jsonMessageHandler) {
        MESSAGE_HANDLER_MAP.put(jsonMessageHandler.type(), jsonMessageHandler);
    }
}
