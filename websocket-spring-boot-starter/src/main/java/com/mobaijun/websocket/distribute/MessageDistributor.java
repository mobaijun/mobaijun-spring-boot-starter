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

/**
 * Description: 消息分发器接口
 * Author: [mobaijun]
 * Date: [2024/8/23 9:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface MessageDistributor {
    /**
     * 消息分发
     * <p>
     * 这个方法用于将发送的消息进行分发处理。
     *
     * @param messageDO 发送的消息，包含消息的详细信息
     */
    void distribute(MessageDTO messageDO);
}
