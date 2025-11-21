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
package com.mobaijun.web.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: [链路追踪配置项]
 * Author: [mobaijun]
 * Date: [2025/11/22 01:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 用于管理链路追踪的基础配置，可通过配置文件进行开关、MDC Key、自定义输出等控制。
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = TraceProperties.PREFIX)
public class TraceProperties {

    public static final String PREFIX = "logging.trace";

    /**
     * 是否启用链路追踪
     * 默认 true，开启后将自动为每个请求生成 traceId
     */
    private boolean enabled = true;

    /**
     * MDC 中使用的 Key 名称
     * 默认 traceId，可自定义
     */
    private String mdcKey = "traceId";

    /**
     * 是否打印 Web 请求信息
     * 可选增强功能
     */
    private boolean logRequest = false;
}
