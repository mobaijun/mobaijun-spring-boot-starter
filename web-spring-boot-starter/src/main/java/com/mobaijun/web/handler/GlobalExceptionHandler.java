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
import com.mobaijun.common.result.R;
import com.mobaijun.core.exception.ServiceException;
import com.mobaijun.core.exception.base.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Description: 全局异常处理器，用于捕获和处理不同类型的异常，并返回适当的响应信息。
 * Author: [mobaijun]
 * Date: [2024/8/13 18:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 HTTP 请求方式不支持异常
     * <p>
     * 当客户端使用不支持的 HTTP 请求方法访问资源时，Spring 会抛出 {@link HttpRequestMethodNotSupportedException} 异常。
     * 本方法捕获此异常，并返回相应的错误信息。
     *
     * @param e       捕获到的 {@link HttpRequestMethodNotSupportedException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含 HTTP 状态码 405 (Method Not Allowed) 和异常信息
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 不支持 '{}' 请求方法", request.getRequestURI(), e.getMethod());
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    /**
     * 处理请求路径中缺少必需的路径变量异常
     *
     * @param e       捕获到的 {@link MissingPathVariableException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含缺少的路径变量信息
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public R<Void> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        log.error("请求路径'{}'中缺少必需的路径变量'{}'，发生系统异常.", request.getRequestURI(), e.getVariableName());
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, String.format("请求路径中缺少必需的路径变量 [%s]", e.getVariableName()));
    }

    /**
     * 处理请求参数类型不匹配异常
     *
     * @param e       捕获到的 {@link MethodArgumentTypeMismatchException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含参数名、要求的类型和输入值
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("请求路径 '{}' 的参数 '{}' 类型不匹配", request.getRequestURI(), e.getName());
        String errorMessage = String.format("请求参数类型不匹配，参数 [%s] 要求类型为：'%s'，但输入值为：'%s'",
                e.getName(), Objects.requireNonNull(e.getRequiredType()).getName(), e.getValue());
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, errorMessage);
    }

    /**
     * 处理找不到路由异常
     *
     * @param e       捕获到的 {@link NoHandlerFoundException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含 404 状态码和错误信息
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public R<Void> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 不存在", request.getRequestURI());
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    /**
     * 处理访问拒绝异常
     *
     * @param e       捕获到的 {@link java.nio.file.AccessDeniedException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含 403 状态码和错误信息
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    public R<Void> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 无权限访问", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.UNAUTHENTICATED, "无权限访问该资源");
    }

    /**
     * 处理文件上传大小超限异常
     *
     * @param e 捕获到的 {@link org.springframework.web.multipart.MaxUploadSizeExceededException} 异常对象
     * @return 封装的响应结果，包含文件上传大小超限的错误信息
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE)
    public R<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件上传大小超限", e);
        return buildErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE, "文件大小超限");
    }

    /**
     * 处理文件读取异常
     *
     * @param e 捕获到的 {@link java.io.IOException} 异常对象
     * @return 封装的响应结果，包含文件读取异常的信息
     */
    @ExceptionHandler(IOException.class)
    public R<Void> handleIOException(IOException e) {
        log.error("文件操作异常", e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "文件操作失败");
    }

    /**
     * 处理未知的运行时异常
     *
     * @param e       捕获到的 {@link RuntimeException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含异常信息
     */
    @ExceptionHandler(RuntimeException.class)
    public R<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生未知异常", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e       捕获到的 {@link Exception} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含异常信息
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生系统异常", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 处理自定义验证异常（绑定异常）
     *
     * @param e 捕获到的 {@link BindException} 异常对象
     * @return 封装的响应结果，包含所有验证错误信息
     */
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(BindException e) {
        log.error("参数绑定异常", e);
        String message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * 处理自定义验证异常（约束违规异常）
     *
     * @param e 捕获到的 {@link ConstraintViolationException} 异常对象
     * @return 封装的响应结果，包含所有约束违规错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("约束违规异常", e);
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * 处理自定义验证异常（方法参数无效异常）
     *
     * @param e 捕获到的 {@link MethodArgumentNotValidException} 异常对象
     * @return 封装的响应结果，包含第一个字段的验证错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("方法参数无效异常", e);
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * 处理空指针异常
     *
     * @param e       捕获到的 {@link NullPointerException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含 500 状态码和错误信息
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生空指针异常", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "系统发生错误{ %s }，请稍后再试!".formatted(e.getMessage()));
    }

    /**
     * 处理业务异常
     *
     * @param e       捕获到的业务异常对象
     * @param request 当前的HTTP请求对象
     * @return 封装的响应结果，包含错误码和错误信息
     */
    @ExceptionHandler(ServiceException.class)
    public R<Void> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error("业务异常发生，异常信息: {}, 请求路径: {}", e.getMessage(), request.getRequestURI());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "系统发生错误{ %s }，请稍后再试!".formatted(e.getMessage()));
    }

    /**
     * 处理基础异常
     *
     * @param e       捕获到的基础异常对象
     * @param request 当前的HTTP请求对象
     * @return 封装的响应结果，包含错误信息
     */
    @ExceptionHandler(BaseException.class)
    public R<Void> handleBaseException(BaseException e, HttpServletRequest request) {
        log.error("基础异常发生，异常信息: {}, 请求路径: {}", e.getMessage(), request.getRequestURI());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "系统发生错误{ %s }，请稍后再试!".formatted(e.getMessage()));
    }

    /**
     * 统一构建错误响应的工具方法
     *
     * @param status  HTTP 状态码
     * @param message 错误信息
     * @return 封装的响应结果
     */
    private R<Void> buildErrorResponse(HttpStatus status, String message) {
        return R.failed(status, message);
    }
}