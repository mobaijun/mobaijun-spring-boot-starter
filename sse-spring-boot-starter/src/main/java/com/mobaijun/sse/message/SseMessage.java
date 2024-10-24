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
package com.mobaijun.sse.message;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Description: [sse 消息实体]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class SseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要推送到的session key 列表
     */
    private List<Long> userIds;

    /**
     * 需要发送的消息
     */
    private String message;

    public SseMessage() {
    }

    public SseMessage(List<Long> userIds, String message) {
        this.userIds = userIds;
        this.message = message;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SseMessage{" +
                "userIds=" + userIds +
                ", message='" + message + '\'' +
                '}';
    }
}
