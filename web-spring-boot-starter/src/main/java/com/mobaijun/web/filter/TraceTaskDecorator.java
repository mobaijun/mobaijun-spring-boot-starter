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

import com.mobaijun.web.trace.TraceIdGenerator;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * Description: [线程池 TraceId 透传装饰器]
 * Author: [mobaijun]
 * Date: [2025/11/22 01:31]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 解决异步线程池、定时任务、@Async 等场景中 TraceId 不继承的问题：
 * - 捕获提交任务时的 TraceId
 * - 在线程执行前注入 MDC
 * - 执行后清理 MDC
 */
@NonNullApi
public record TraceTaskDecorator(String mdcKey) implements TaskDecorator {

    /**
     * 全局 TraceId 生成器，可通过 setGenerator 注入
     */
    private static TraceIdGenerator generator;

    /**
     * 设置全局 TraceId 生成器
     *
     * @param generator TraceId 生成器实例
     */
    public static void setGenerator(TraceIdGenerator generator) {
        TraceTaskDecorator.generator = generator;
    }

    /**
     * 线程池任务装饰方法
     *
     * @param runnable 提交到线程池的原始任务
     * @return 包装后的 Runnable，执行时保证 MDC 中有 traceId
     */
    @Override
    public Runnable decorate(@Nullable Runnable runnable) {
        return () -> {
            try {
                // 获取提交线程的 traceId
                String traceId = MDC.get(mdcKey);

                // 如果没有 traceId，则使用全局生成器生成新的 traceId
                if (traceId == null || traceId.isEmpty()) {
                    traceId = generator.generate();
                }

                // 将 traceId 注入当前线程 MDC
                MDC.put(mdcKey, traceId);

                // 执行原始任务
                assert runnable != null;
                runnable.run();
            } finally {
                // 执行完毕清理 MDC，避免线程池线程复用污染
                MDC.remove(mdcKey);
            }
        };
    }
}
