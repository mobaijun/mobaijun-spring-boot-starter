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

import com.mobaijun.common.thread.Threads;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Description: [线程池配置]
 * Author: [mobaijun]
 * Date: [2024/8/12 9:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
public class ThreadPoolConfig {

    /**
     * 日志配置
     */
    private static final Log log = LogFactory.getLog(ThreadPoolConfig.class);

    /**
     * 核心线程数 = cpu 核心数 + 1
     */
    private final int core = Runtime.getRuntime().availableProcessors() + 1;

    private ScheduledExecutorService scheduledExecutorService;

    /**
     * 创建一个配置化的 {@link ScheduledExecutorService}，用于执行周期性或定时任务。
     *
     * <p>该线程池基于 {@link ThreadPoolTaskExecutor} 实现，并具有以下特性：
     * - **核心线程数：** 由 `core` 参数指定。
     * - **线程命名：** 采用 "schedule-pool-%d" 格式。
     * - **守护线程：** 设置为守护线程，随 JVM 退出而终止。
     * - **拒绝策略：** 采用 `CallerRunsPolicy`，任务提交失败时由调用者线程执行。
     *
     * @return 配置好的 {@link ScheduledExecutorService} 实例
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        // 使用 ThreadPoolTaskExecutor 创建线程池，并进行配置
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(core);
        executor.setThreadNamePrefix("schedule-pool-");
        executor.setDaemon(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        // 将 ThreadPoolTaskExecutor 包装为 ScheduledExecutorService
        ScheduledExecutorService scheduledExecutorService = Executors.
                newScheduledThreadPool(executor.getCorePoolSize(), executor);
        this.scheduledExecutorService = scheduledExecutorService;
        return scheduledExecutorService;
    }

    /**
     * 销毁事件
     */
    @PreDestroy
    public void destroy() {
        try {
            log.info("====关闭后台任务任务线程池====");
            Threads.shutdownAndAwaitTermination(scheduledExecutorService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
