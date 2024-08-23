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
package com.mobaijun.websocket.constant;

/**
 * Description: websocket 消息分发器类型常量类
 * Author: [mobaijun]
 * Date: [2024/8/23 9:50]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public final class MessageDistributorTypeConstants {

    /**
     * 本地
     */
    public static final String LOCAL = "local";
    /**
     * 基于 Redis PUB/SUB
     */
    public static final String REDIS = "redis";
    /**
     * 基于 rocketmq 广播
     */
    public static final String ROCKETMQ = "rocketmq";
    /**
     * 自定义
     */
    public static final String CUSTOM = "custom";

    private MessageDistributorTypeConstants() {
    }
}
