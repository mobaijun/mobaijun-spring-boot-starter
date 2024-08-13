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

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Description: [任务执行器配置]
 * Author: [mobaijun]
 * Date: [2024/7/30 14:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
public class TaskExecutorConfiguration implements AsyncConfigurer {

    /**
     * 获取当前机器的核数, 不一定准确 请根据实际场景 CPU密集 || IO 密集
     */
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    @Value("${thread.pool.corePoolSize:}")
    private Optional<Integer> corePoolSize;

    @Value("${thread.pool.maxPoolSize:}")
    private Optional<Integer> maxPoolSize;

    @Value("${thread.pool.queueCapacity:}")
    private Optional<Integer> queueCapacity;

    @Value("${thread.pool.awaitTerminationSeconds:}")
    private Optional<Integer> awaitTerminationSeconds;

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程大小 默认区 CPU 数量
        taskExecutor.setCorePoolSize(corePoolSize.orElse(cpuNum));
        // 最大线程大小 默认区 CPU * 2 数量
        taskExecutor.setMaxPoolSize(maxPoolSize.orElse(cpuNum * 2));
        // 队列最大容量
        taskExecutor.setQueueCapacity(queueCapacity.orElse(500));
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds.orElse(60));
        taskExecutor.setThreadNamePrefix("PIG-Thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
