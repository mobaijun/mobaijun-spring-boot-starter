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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

/**
 * Description: 并发 WebSocketSession 配置类
 * Author: [mobaijun]
 * Date: [2024/8/23 9:27]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class ConcurrentWebSocketSessionOptions {

    /**
     * 溢出时的执行策略
     */
    ConcurrentWebSocketSessionDecorator.OverflowStrategy overflowStrategy = ConcurrentWebSocketSessionDecorator.OverflowStrategy.TERMINATE;
    /**
     * 是否在多线程环境下进行发送
     */
    private boolean enable = false;
    /**
     * 发送时间的限制（ms）
     */
    private int sendTimeLimit = 1000 * 5;
    /**
     * 发送消息缓冲上限 (byte)
     */
    private int bufferSizeLimit = 1024 * 100;
}
