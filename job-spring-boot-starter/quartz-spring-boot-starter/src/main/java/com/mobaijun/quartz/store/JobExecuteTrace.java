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

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Description: 任务执行记录
 * Author: [mobaijun]
 * Date: [2024/8/20 10:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class JobExecuteTrace implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 分组名称
     */
    private String jobGroup;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务描述
     */
    private String jobDescription;

    /**
     * 任务类型
     */
    private String triggerType;

    /**
     * 执行策略
     */
    private String jobStrategy;

    /**
     * 任务类
     */
    private String jobClass;

    /**
     * 任务参数
     */
    private String jobParams;

    /**
     * 执行时间
     */
    private LocalDateTime executeTime;

    /**
     * 执行结果
     */
    private String executeState;

    /**
     * 执行时间
     */
    private Long runTime;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 上一次执行时间
     */
    private LocalDateTime previousFireTime;

    /**
     * 下一次执行时间
     */
    private LocalDateTime nextFireTime;

    /**
     * 任务消息
     */
    private String message;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 实例名称
     */
    private String instanceName;
}
