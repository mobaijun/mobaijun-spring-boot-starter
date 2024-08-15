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
package com.mobaijun.log.aspect;

import com.mobaijun.log.annotation.OperationLog;
import com.mobaijun.log.handler.OperationLogHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;

/**
 * Description: [操作日志切面类]
 * Author: [mobaijun]
 * Date: [2024/8/15 11:04]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class OperationLogAspect<T> {

    /**
     * 操作日志处理器
     */
    private final OperationLogHandler<T> operationLogHandler;

    /**
     * 操作日志切面
     *
     * @param joinPoint 切点
     * @return 切面
     * @throws Throwable 异常
     */
    @Around("execution(@(@com.mobaijun.log.annotation.OperationLog *) * *(..)) " + "|| @annotation(com.mobaijun.log.annotation.OperationLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始时间
        long startTime = System.currentTimeMillis();

        // 获取目标方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取操作日志注解 备注： AnnotationUtils.findAnnotation 方法无法获取继承的属性
        OperationLog operationLogging = AnnotatedElementUtils.findMergedAnnotation(method, OperationLog.class);

        // 获取操作日志 DTO
        Assert.notNull(operationLogging, "operationLogging annotation must not be null!");

        T operationLog = this.operationLogHandler.buildLog(operationLogging, joinPoint);

        Throwable throwable = null;
        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            // 是否保存响应内容
            boolean isSaveResult = operationLogging.recordResult();
            // 操作日志记录处理
            handleLog(joinPoint, startTime, operationLog, throwable, isSaveResult, result);
        }
    }

    /**
     * 操作日志记录处理
     *
     * @param joinPoint    切点
     * @param startTime    开始时间
     * @param operationLog 操作日志
     * @param throwable    异常信息
     * @param isSaveResult 保存响应内容
     * @param result       响应内容
     */
    private void handleLog(ProceedingJoinPoint joinPoint, long startTime, T operationLog, Throwable throwable, boolean isSaveResult, Object result) {
        try {
            // 结束时间
            long executionTime = System.currentTimeMillis() - startTime;
            // 记录执行信息
            this.operationLogHandler.recordExecutionInfo(operationLog, joinPoint, executionTime, throwable, isSaveResult, result);
            // 处理操作日志
            this.operationLogHandler.handleLog(operationLog);
        } catch (Exception e) {
            log.error("记录操作日志异常：{}", operationLog);
        }
    }
}
