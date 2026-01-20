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
package com.mobaijun.web.handler;

import com.mobaijun.common.enums.http.HttpStatus;
import com.mobaijun.common.result.ErrorDataInfo;
import com.mobaijun.common.result.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchExecutorException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description: MyBatis 相关异常处理器，仅在 MyBatis 存在时生效
 * Author: [mobaijun]
 * Date: [2024/8/13 18:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "org.apache.ibatis.exceptions.PersistenceException")
public class MyBatisExceptionHandler extends BaseExceptionHandler {

    /**
     * 处理 MyBatis 批量操作失败的异常
     * <p>
     * 当执行 MyBatis 批量操作时出现错误（例如 SQL 语句错误）会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link BatchExecutorException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BatchExecutorException.class)
    public R<ErrorDataInfo> handleBatchExecutorException(BatchExecutorException e, HttpServletRequest request) {
        log.error("批量执行失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, 
                "批量操作执行失败，可能由于 SQL 语句错误或数据格式问题，请稍后重试！", null);
    }

    /**
     * 处理 MyBatis 持久化操作失败的异常
     * <p>
     * 当 MyBatis 执行持久化操作（如插入、更新、删除、查询）失败时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link PersistenceException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(PersistenceException.class)
    public R<ErrorDataInfo> handlePersistenceException(PersistenceException e, HttpServletRequest request) {
        log.error("持久化操作失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, 
                "持久化操作失败，可能由于数据库连接错误或数据问题，请稍后重试！", null);
    }

    /**
     * 处理 MyBatis 构建配置文件或映射文件失败的异常
     * <p>
     * 当 MyBatis 构建配置文件或映射文件时出现错误，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link BuilderException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BuilderException.class)
    public R<ErrorDataInfo> handleBuilderException(BuilderException e, HttpServletRequest request) {
        log.error("MyBatis 配置文件或映射文件构建失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, 
                "SQL 构建失败，可能由于配置文件错误或映射问题，请联系开发人员！", null);
    }
}