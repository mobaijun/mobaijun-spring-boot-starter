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
package com.mobaijun.xxljob.trace;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import java.util.UUID;
import org.slf4j.MDC;

/**
 * Description: 可追踪的 MethodJobHandler.
 * Author: [mobaijun]
 * Date: [2024/8/20 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class TraceMethodJobHandler extends IJobHandler {

    /**
     * 用于 MDC 的 key
     */
    private final static String TRACE_ID = "traceId";

    /**
     * 目标 MethodJobHandler
     */
    private final MethodJobHandler methodJobHandler;

    /**
     * 构造方法
     *
     * @param target 目标 MethodJobHandler
     */
    public TraceMethodJobHandler(MethodJobHandler target) {
        this.methodJobHandler = target;
    }

    @Override
    public void execute() throws Exception {
        MDC.put(TRACE_ID, generateTraceId());
        try {
            this.methodJobHandler.execute();
        } finally {
            MDC.remove(TRACE_ID);
        }
    }

    @Override
    public void init() throws Exception {
        this.methodJobHandler.init();
    }

    @Override
    public void destroy() throws Exception {
        this.methodJobHandler.destroy();
    }

    @Override
    public String toString() {
        return this.methodJobHandler.toString();
    }

    /**
     * 生成 traceId, 默认使用 jobId 拼接无下划线的 UUID
     *
     * @return traceId
     */
    protected String generateTraceId() {
        long jobId = XxlJobHelper.getJobId();
        String simpleUUID = UUID.randomUUID().toString().replace("-", "");
        return jobId + "-" + simpleUUID;
    }
}
