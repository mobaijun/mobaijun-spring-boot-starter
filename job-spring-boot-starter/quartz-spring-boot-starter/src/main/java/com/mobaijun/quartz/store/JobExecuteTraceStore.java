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
 * Description: 任务执行记录存储接口，提供持久化与清理能力
 * Author: [mobaijun]
 * Date: [2024/8/20 10:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface JobExecuteTraceStore {

    /**
     * 存储执行记录
     *
     * @param jobExecuteTrace 执行记录
     * @return 返回生成的记录ID，如果数据库不支持自增ID则返回null
     */
    Long storeTrace(JobExecuteTrace jobExecuteTrace);

    /**
     * 更新执行记录
     * <p>
     * 根据记录ID更新已存在的任务执行记录，通常用于更新任务执行完成后的状态、执行时间、错误信息等
     * </p>
     *
     * @param jobExecuteTrace 执行记录（必须包含id字段）
     */
    void updateTrace(JobExecuteTrace jobExecuteTrace);

    /**
     * 清理过期记录
     *
     * @param cleanDays 清理天数
     */
    void cleanUp(int cleanDays);
}
