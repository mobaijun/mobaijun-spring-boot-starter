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
package com.mobaijun.log.handler;

import com.mobaijun.log.annotation.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Description: [操作日志处理器，自行实现接口]
 * Author: [mobaijun]
 * Date: [2024/8/15 11:07]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public interface OperationLogHandler<T> {

    /**
     * 创建操作日志
     *
     * @param operationLog 操作日志注解，用于提取日志相关的信息
     * @param joinPoint    当前执行方法的切点信息，用于获取方法执行过程中的信息
     * @return 返回操作日志对象，用于记录操作日志信息
     */
    T buildLog(OperationLog operationLog, ProceedingJoinPoint joinPoint);

    /**
     * 目标方法执行完成后进行信息补充记录, 如执行时长，异常信息，还可以通过切点记录返回值，如果需要的话
     *
     * @param log           操作日志对象 {@link #buildLog}
     * @param joinPoint     当前执行方法的切点信息
     * @param executionTime 方法执行时长
     * @param throwable     方法执行的异常，为 null 则表示无异常
     * @param isSaveResult  是否记录返回值
     * @param result        方法执行的返回值
     * @return T 操作日志对象
     */
    T recordExecutionInfo(T log, ProceedingJoinPoint joinPoint, long executionTime, Throwable throwable,
                          boolean isSaveResult, Object result);

    /**
     * 处理日志，可以在这里进行存储，或者输出
     *
     * @param operationLog 操作日志
     */
    void handleLog(T operationLog);
}
