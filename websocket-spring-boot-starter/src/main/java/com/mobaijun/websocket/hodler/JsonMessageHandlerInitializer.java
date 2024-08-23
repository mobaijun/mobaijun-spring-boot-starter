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
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * Description:
 * <p>
 * JsonMessageHandler 初始化器
 * <p/>
 * 将所有的 jsonMessageHandler 收集到 JsonMessageHandlerHolder 中
 * Author: [mobaijun]
 * Date: [2024/8/23 9:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
public class JsonMessageHandlerInitializer {

    /**
     * 所有的 jsonMessageHandler
     */
    private final List<JsonMessageHandler<? extends JsonWebSocketMessage>> jsonMessageHandlerList;

    /**
     * 初始化 jsonMessageHandlerHolder
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void initJsonMessageHandlerHolder() {
        for (JsonMessageHandler<? extends JsonWebSocketMessage> jsonMessageHandler : this.jsonMessageHandlerList) {
            JsonMessageHandlerHolder.addHandler((JsonMessageHandler<JsonWebSocketMessage>) jsonMessageHandler);
        }
    }
}
