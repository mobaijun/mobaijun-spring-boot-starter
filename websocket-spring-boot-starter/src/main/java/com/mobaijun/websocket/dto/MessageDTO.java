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
package com.mobaijun.websocket.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Description: 消息传输对象
 * Author: [mobaijun]
 * Date: [2024/8/23 9:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MessageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否广播
     */
    private Boolean needBroadcast;

    /**
     * 对于拥有相同 sessionKey 的客户端，仅对其中的一个进行发送
     */
    private Boolean onlyOneClientInSameKey;

    /**
     * 需要发送的 sessionKeys 集合，当广播时，不需要
     */
    private List<Object> sessionKeys;

    /**
     * 需要发送的消息文本
     */
    private String messageText;
}
