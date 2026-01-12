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
package com.mobaijun.quartz.event;

import com.mobaijun.quartz.enums.JobExecuteTraceType;
import com.mobaijun.quartz.store.JobExecuteTrace;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * Description: 任务执行记录事件
 * Author: [mobaijun]
 * Date: [2024/8/20 9:59]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class JobExecuteTraceEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    private final JobExecuteTrace jobExecuteTrace;

    private JobExecuteTraceType eventType;

    /**
     * 构造方法
     *
     * @param jobExecuteTrace JobExecuteTrace
     * @param eventType       {@link JobExecuteTraceType}
     */
    public JobExecuteTraceEvent(JobExecuteTrace jobExecuteTrace, JobExecuteTraceType eventType) {
        super(jobExecuteTrace.getJobName());
        this.jobExecuteTrace = jobExecuteTrace;
        this.eventType = eventType;
    }
}
