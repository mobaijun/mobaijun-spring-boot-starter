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
package com.mobaijun.quartz.job;

import com.mobaijun.quartz.store.JobExecuteTraceStore;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Description: 清理 {@link JobExecuteTraceStore} 中的记录的历史数据Job
 * Author: [mobaijun]
 * Date: [2024/8/20 9:59]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CleanExecuteTraceJob extends QuartzJobBean {

    /**
     * 存储执行记录的存储器
     */
    private final JobExecuteTraceStore jobExecuteTraceStore;

    @Override
    protected void executeInternal(@NonNull JobExecutionContext context) {
        JobDataMap params = context.getMergedJobDataMap();
        final int cleanDays = params.getInt("cleanDays");
        this.jobExecuteTraceStore.cleanUp(cleanDays);
    }
}
