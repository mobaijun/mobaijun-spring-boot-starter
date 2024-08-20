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
package com.mobaijun.quartz.event.listener;

import com.mobaijun.quartz.event.JobExecuteTraceEvent;
import com.mobaijun.quartz.store.JobExecuteTraceStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Description: 任务执行记录事件监听
 * Author: [mobaijun]
 * Date: [2024/8/20 10:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RequiredArgsConstructor
public class JobExecuteTraceStoreListener {

    private final JobExecuteTraceStore jobExecuteTraceStore;

    /**
     * 处理异步保存任务执行记录事件
     *
     * @param event {@link JobExecuteTraceEvent}
     */
    @Async
    @EventListener(JobExecuteTraceEvent.class)
    public void handleJobExecuteTraceEvent(JobExecuteTraceEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("接收到异步存储任务执行记录事件{}，处理中...", event);
        }
        switch (event.getEventType()) {
            case UPDATE:
            case INITIALIZE:
                this.jobExecuteTraceStore.storeTrace(event.getJobExecuteTrace());
                break;
            default:
                log.error("未知的事件类型:{}", event.getEventType());
        }
    }
}

