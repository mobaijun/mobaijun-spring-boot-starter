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
package com.mobaijun.ratelimiter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: [限流配置]
 * Author: [mobaijun]
 * Date: [2024/11/14 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConfigurationProperties(RateLimiterProperties.PREFIX)
public class RateLimiterProperties {

    /**
     * redis 配置
     */
    public static final String PREFIX = "spring.data.redis";

    /**
     * redis 配置地址
     */
    private String host = "localhost";

    /**
     * redis 配置端口
     */
    private int port = 6379;

    /**
     * redis 配置密码
     */
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}