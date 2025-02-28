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

import org.springframework.web.socket.WebSocketSession;


/**
 * Description: WebSocketSession 唯一标识生成器
 * Author: [mobaijun]
 * Date: [2024/8/23 9:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface SessionKeyGenerator {

    /**
     * 获取当前session的唯一标识
     *
     * @param webSocketSession 当前session
     * @return session唯一标识
     */
    Object sessionKey(WebSocketSession webSocketSession);
}
