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
package com.mobaijun.quartz.store;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/8/20 10:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface JobExecuteTraceStore {

    /**
     * 存储执行记录
     *
     * @param jobExecuteTrace 执行记录
     */
    void storeTrace(JobExecuteTrace jobExecuteTrace);

    /**
     * 清理过期记录
     *
     * @param cleanDays 清理天数
     */
    void cleanUp(int cleanDays);
}
