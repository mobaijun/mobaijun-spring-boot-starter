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
public record TraceTaskDecorator(String mdcKey, TraceIdGenerator generator) implements TaskDecorator {

    /**
     * 构造函数
     *
     * @param mdcKey    MDC 中使用的 Key
     * @param generator TraceId 生成器
     */
    public TraceTaskDecorator {
    }

    /**
     * 线程池任务装饰方法
     *
     * @param runnable 提交到线程池的原始任务
     * @return 包装后的 Runnable，执行时保证 MDC 中有 traceId
     */
    @Override
    public Runnable decorate(@Nullable Runnable runnable) {
        // 在提交线程中获取当前的 traceId（MDC 是 ThreadLocal，会在线程切换时丢失）
        String traceId = MDC.get(mdcKey);

        return () -> {
            try {
                // 如果提交线程没有 traceId，则生成新的
                if (traceId == null || traceId.isEmpty()) {
                    String newTraceId = generator.generate();
                    MDC.put(mdcKey, newTraceId);
                } else {
                    // 将提交线程的 traceId 注入当前执行线程的 MDC
                    MDC.put(mdcKey, traceId);
                }

                // 执行原始任务
                if (runnable != null) {
                    runnable.run();
                }
            } finally {
                // 执行完毕清理 MDC，避免线程池线程复用污染
                MDC.remove(mdcKey);
            }
        };
    }
}
