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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description: 验证相关异常处理器
 * Author: [mobaijun]
 * Date: [2025/11/22]
 * <p>
 * 处理参数验证相关的异常，仅在存在 Spring Validation 支持时生效。
 * 包括：
 * - BindException: 请求参数绑定失败
 * - ConstraintViolationException: 请求参数违反约束条件
 * - MethodArgumentNotValidException: 方法参数验证失败
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "org.springframework.validation.BindException")
public class ValidationExceptionHandler extends BaseExceptionHandler {

    /**
     * 处理自定义验证异常（绑定异常）
     * <p>
     * 当请求参数绑定失败时，Spring 会抛出 {@link BindException} 异常。
     * 本方法捕获此异常，记录所有验证错误信息，并返回封装的响应结果。
     * </p>
     *
     * @param e       捕获到的 {@link BindException} 异常对象
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的响应结果，包含所有验证错误信息
     */
    @ExceptionHandler(BindException.class)
    public R<ErrorDataInfo> handleBindException(BindException e, HttpServletRequest request) {
        String message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("请求参数绑定异常，验证错误信息：{}", message);
        String errorMessage = String.format("请求参数无效，验证错误信息：'%s'", message);
        return handleException(e, request, HttpStatus.BAD_REQUEST, errorMessage, "");
    }

    /**
     * 处理自定义验证异常（约束违规异常）
     * <p>
     * 当请求参数违反约束条件时，Spring 会抛出 {@link ConstraintViolationException} 异常。
     * 本方法捕获此异常，记录所有约束违规错误信息，并返回封装的响应结果。
     * </p>
     *
     * @param e       捕获到的 {@link ConstraintViolationException} 异常对象
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的响应结果，包含所有约束违规错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<ErrorDataInfo> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("请求参数违反约束条件，约束错误信息：{}", message);
        String errorMessage = String.format("请求参数违反约束条件，约束错误信息：'%s'", message);
        return handleException(e, request, HttpStatus.BAD_REQUEST, errorMessage, "");
    }

    /**
     * 处理自定义验证异常（方法参数无效异常）
     * <p>
     * 当方法参数验证失败时，Spring 会抛出 {@link MethodArgumentNotValidException} 异常。
     * 本方法捕获此异常，记录第一个字段的验证错误信息，并返回封装的响应结果。
     * </p>
     *
     * @param e       捕获到的 {@link MethodArgumentNotValidException} 异常对象
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的响应结果，包含第一个字段的验证错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<ErrorDataInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("方法参数无效，验证失败，字段错误信息：{}", message);
        String errorMessage = String.format("方法参数无效，验证失败，字段错误信息：'%s'", message);
        return handleException(e, request, HttpStatus.BAD_REQUEST, errorMessage, "");
    }
}