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
package com.mobaijun.websocket.session;

import com.mobaijun.websocket.handler.ConcurrentWebSocketSessionOptions;
import io.micrometer.common.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * Description: WebSocketHandler 装饰器，该装饰器主要用于在开启和关闭连接时，进行session的映射存储与释放
 * Author: [mobaijun]
 * Date: [2024/8/23 9:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class MapSessionWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    /**
     * 存储session的容器
     */
    private final WebSocketSessionStore webSocketSessionStore;

    /**
     * 并发websocket配置
     */
    private final ConcurrentWebSocketSessionOptions concurrentWebSocketSessionOptions;

    /**
     * 构造函数
     *
     * @param delegate                          装饰器
     * @param webSocketSessionStore             存储session的容器
     * @param concurrentWebSocketSessionOptions 并发websocket配置
     */
    public MapSessionWebSocketHandlerDecorator(WebSocketHandler delegate, WebSocketSessionStore webSocketSessionStore,
                                               ConcurrentWebSocketSessionOptions concurrentWebSocketSessionOptions) {
        super(delegate);
        this.webSocketSessionStore = webSocketSessionStore;
        this.concurrentWebSocketSessionOptions = concurrentWebSocketSessionOptions;
    }

    /**
     * websocket 连接时执行的动作
     *
     * @param wsSession websocket session 对象
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession wsSession) {
        // 包装一层，防止并发发送出现问题
        if (Boolean.TRUE.equals(this.concurrentWebSocketSessionOptions.isEnable())) {
            wsSession = new ConcurrentWebSocketSessionDecorator(wsSession,
                    this.concurrentWebSocketSessionOptions.getSendTimeLimit(),
                    this.concurrentWebSocketSessionOptions.getBufferSizeLimit(),
                    this.concurrentWebSocketSessionOptions.getOverflowStrategy());
        }
        this.webSocketSessionStore.addSession(wsSession);
    }

    /**
     * websocket 关闭连接时执行的动作
     *
     * @param wsSession   websocket session 对象
     * @param closeStatus 关闭状态对象
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession wsSession, @NonNull CloseStatus closeStatus) throws Exception {
        this.webSocketSessionStore.removeSession(wsSession);
    }
}
