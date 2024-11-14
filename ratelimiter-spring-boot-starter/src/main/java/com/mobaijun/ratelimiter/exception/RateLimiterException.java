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
package com.mobaijun.ratelimiter.exception;

/**
 * Description: [限流配置]
 * Author: [mobaijun]
 * Date: [2024/11/14 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class RateLimiterException extends RuntimeException {

    /**
     * IP 地址
     */
    private String ip;

    /**
     * 构造器，传入消息
     *
     * @param message 消息
     */
    public RateLimiterException(String message) {
        super(message);
    }

    /**
     * 构造器，传入消息和 IP 地址
     *
     * @param message 消息
     * @param ip      IP 地址
     */
    public RateLimiterException(String message, String ip) {
        super(message);
        this.ip = ip;
    }

    // 获取 IP 地址
    public String getIp() {
        return ip;
    }

    /**
     * 设置 IP 地址
     *
     * @param ip IP 地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}