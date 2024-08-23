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
package com.mobaijun.websocket.message;

import lombok.Getter;

/**
 * Description:
 * <p>
 * 自定义的 Json 类型的消息
 * </p>
 * <ul>
 * <li>要求消息内容必须是一个 Json 对象
 * <li>Json 对象中必须有一个属性 type
 * <ul/>
 * Author: [mobaijun]
 * Date: [2024/8/23 9:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
public abstract class JsonWebSocketMessage {

    /**
     * 消息类型
     */
    public static final String TYPE_FIELD = "type";

    /**
     * 消息类型
     */
    private final String type;

    /**
     * 构造函数
     *
     * @param type 消息类型
     */
    protected JsonWebSocketMessage(String type) {
        this.type = type;
    }
}
