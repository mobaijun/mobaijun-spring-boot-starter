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

import com.mobaijun.websocket.handler.CustomWebSocketHandler;
import com.mobaijun.websocket.handler.PlanTextMessageHandler;
import com.mobaijun.websocket.properties.WebSocketProperties;
import com.mobaijun.websocket.session.DefaultWebSocketSessionStore;
import com.mobaijun.websocket.session.MapSessionWebSocketHandlerDecorator;
import com.mobaijun.websocket.session.SessionKeyGenerator;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;

/**
 * Description:
 * WebSocket 处理器配置类
 * <p>此配置类用于配置 WebSocket 会话存储器和处理器，支持自定义会话键生成器和消息处理器。
 * Author: [mobaijun]
 * Date: [2024/8/23 9:25]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
public class WebSocketHandlerConfig {

    /**
     * WebSocket 配置属性，通过构造方法注入
     */
    private final WebSocketProperties webSocketProperties;

    /**
     * 配置 WebSocket 会话存储器。
     *
     * <p>如果没有其他自定义的 WebSocketSessionStore 实例被注册，则创建一个默认的会话存储器。
     * 可以通过可选的 SessionKeyGenerator 自定义会话键的生成方式。
     *
     * @param sessionKeyGenerator 可选的会话键生成器，默认为空
     * @return 默认的 WebSocket 会话存储器实例
     */
    @Bean
    @ConditionalOnMissingBean
    public WebSocketSessionStore webSocketSessionStore(
            @Autowired(required = false) SessionKeyGenerator sessionKeyGenerator) {
        return new DefaultWebSocketSessionStore(sessionKeyGenerator);
    }

    /**
     * 配置 WebSocket 处理器。
     *
     * <p>如果没有其他自定义的 WebSocketHandler 实例被注册，则创建一个默认的 WebSocket 处理器。
     * 可以通过可选的 PlanTextMessageHandler 自定义消息的处理方式。
     * 当启用会话映射时，返回一个带有 MapSessionWebSocketHandlerDecorator 的装饰器处理器。
     *
     * @param webSocketSessionStore  WebSocket 会话存储器实例
     * @param planTextMessageHandler 可选的文本消息处理器，默认为空
     * @return 配置好的 WebSocket 处理器实例
     */
    @Bean
    @ConditionalOnMissingBean(WebSocketHandler.class)
    public WebSocketHandler webSocketHandler(WebSocketSessionStore webSocketSessionStore,
                                             @Autowired(required = false) PlanTextMessageHandler planTextMessageHandler) {
        CustomWebSocketHandler customWebSocketHandler = new CustomWebSocketHandler(planTextMessageHandler);
        if (this.webSocketProperties.isMapSession()) {
            return new MapSessionWebSocketHandlerDecorator(customWebSocketHandler, webSocketSessionStore,
                    this.webSocketProperties.getConcurrent());
        }
        return customWebSocketHandler;
    }
}
