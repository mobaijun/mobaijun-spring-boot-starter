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
package com.mobaijun.web.filter;

import com.mobaijun.web.properties.TraceProperties;
import com.mobaijun.web.trace.TraceIdGenerator;
import jakarta.servlet.*;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * Description: [链路追踪过滤器]
 * Author: [mobaijun]
 * Date: [2025/11/22 01:31]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 基于 Servlet Filter 的 TraceId 注入方案：
 * - 每个请求都会检查是否已有 TraceId
 * - 若无则自动生成并写入 MDC
 * - 响应结束后清理 MDC 防止线程污染
 */
public record TraceFilter(TraceProperties properties, TraceIdGenerator generator) implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 链路追踪开关控制
        if (!properties.isEnabled()) {
            chain.doFilter(request, response);
            return;
        }

        String key = properties.getMdcKey();
        String traceId = MDC.get(key);

        // 若不存在 traceId，则生成新的
        if (traceId == null) {
            traceId = generator.generate();
            MDC.put(key, traceId);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            // 防止线程复用导致 TraceId 泄漏
            MDC.remove(key);
        }
    }
}
