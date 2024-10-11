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
package com.mobaijun.core.config;

import com.mobaijun.common.exception.ServiceException;
import com.mobaijun.core.spring.SpringUtil;
import java.util.Arrays;
import java.util.concurrent.Executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * Description: [异步配置]
 * Author: [mobaijun]
 * Date: [2024/7/30 10:10]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(AsyncConfig.class);

    /**
     * 自定义 @Async 注解使用系统线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        if (SpringUtil.isVirtual()) {
            return new VirtualThreadTaskExecutor("async-");
        }
        return SpringUtil.getBean("scheduledExecutorService");
    }

    /**
     * 异步执行异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("Async exception message - " + throwable.getMessage());
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message - ").append(throwable.getMessage())
                    .append(", Method name - ").append(method.getName());
            if (!Arrays.stream(objects).toList().isEmpty()) {
                sb.append(", Parameter value - ").append(Arrays.toString(objects));
            }
            throw new ServiceException(sb.toString());
        };
    }
}
