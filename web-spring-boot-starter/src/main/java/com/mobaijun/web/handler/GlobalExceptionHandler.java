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
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
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
     * 处理缺失请求参数异常
     *
     * @param e 捕获到的 {@link MissingServletRequestParameterException} 异常对象
     * @return 封装的响应结果，包含缺失的请求参数信息
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("请求缺少必需的请求参数 '{}'", e.getParameterName());
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, String.format("缺失请求参数: [%s]", e.getParameterName()));
    }

    /**
     * 处理不支持的媒体类型异常
     *
     * @param e 捕获到的 {@link HttpMediaTypeNotSupportedException} 异常对象
     * @return 封装的响应结果，包含不支持的媒体类型信息
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<Void> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持的媒体类型: {}", e.getContentType());
        return buildErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, String.format("不支持的媒体类型: [%s]", e.getContentType()));
    }

    /**
     * 处理找不到元素异常
     *
     * @param e 捕获到的 {@link NoSuchElementException} 异常对象
     * @return 封装的响应结果，包含找不到的元素信息
     */
    @ExceptionHandler(NoSuchElementException.class)
    public R<Void> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        log.error("请求的资源在数据库中不存在: {}, 请求路径: {}", e.getMessage(), request.getRequestURI());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "请求的资源不存在");
    }

    /**
     * 处理 SQL 异常
     *
     * @param e 捕获到的 {@link SQLException} 异常对象
     * @return 封装的响应结果，包含 SQL 错误信息
     */
    @ExceptionHandler(SQLException.class)
    public R<Void> handleSQLException(SQLException e) {
        log.error("SQL 执行异常: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "数据库操作失败，请稍后再试！");
    }

    /**
     * 处理数据完整性异常
     *
     * @param e 捕获到的 {@link DataIntegrityViolationException} 异常对象
     * @return 封装的响应结果，包含详细的错误信息
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<Void> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("数据完整性违规: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.PERMANENT_REDIRECT, "数据操作失败，可能违反了数据库的约束条件！");
    }

    /**
     * 处理空结果异常
     *
     * @param e 捕获到的 {@link EmptyResultDataAccessException} 异常对象
     * @return 封装的响应结果，提示资源未找到
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public R<Void> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("查询的资源不存在: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "查询的资源不存在！");
    }

    /**
     * 处理数据访问资源失败异常
     *
     * @param e 捕获到的 {@link DataAccessResourceFailureException} 异常对象
     * @return 封装的响应结果，包含数据库连接失败的提示信息
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public R<Void> handleDataAccessResourceFailureException(DataAccessResourceFailureException e) {
        log.error("数据库访问资源失败: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "数据库连接失败，请稍后重试！");
    }

    /**
     * 处理乐观锁异常
     *
     * @param e 捕获到的 {@link OptimisticLockingFailureException} 异常对象
     * @return 封装的响应结果，提示乐观锁失败
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public R<Void> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        log.error("乐观锁失败: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "数据版本冲突，请重试！");
    }

    /**
     * 处理结果大小不匹配异常
     *
     * @param e 捕获到的 {@link IncorrectResultSizeDataAccessException} 异常对象
     * @return 封装的响应结果，提示数据不一致
     */
    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public R<Void> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e) {
        log.error("查询结果数量异常: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "查询结果数量不匹配！");
    }

    /**
     * 处理网络请求超时异常
     *
     * @param e 捕获到的 {@link java.net.SocketTimeoutException} 异常对象
     * @return 封装的响应结果，包含超时错误信息
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public R<Void> handleSocketTimeoutException(SocketTimeoutException e) {
        log.error("网络请求超时", e);
        return buildErrorResponse(HttpStatus.GATEWAY_TIMEOUT, "网络请求超时，请稍后重试");
    }

    /**
     * 处理网络连接异常
     *
     * @param e 捕获到的 {@link java.net.ConnectException} 异常对象
     * @return 封装的响应结果，包含连接错误信息
     */
    @ExceptionHandler(ConnectException.class)
    public R<Void> handleConnectException(ConnectException e) {
        log.error("网络连接失败", e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "无法连接到服务，请稍后再试");
    }

    /**
     * 处理主机未知异常
     *
     * @param e 捕获到的 {@link java.net.UnknownHostException} 异常对象
     * @return 封装的响应结果，包含主机错误信息
     */
    @ExceptionHandler(UnknownHostException.class)
    public R<Void> handleUnknownHostException(UnknownHostException e) {
        log.error("未知主机", e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "无法解析主机地址，请检查网络连接");
    }

    /**
     * 处理 HTTP 客户端异常
     *
     * @param e 捕获到的 {@link org.springframework.web.client.HttpClientErrorException} 异常对象
     * @return 封装的响应结果，包含 HTTP 客户端错误信息
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public R<Void> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("HTTP 客户端异常: 状态码 {}, 错误信息 {}", e.getStatusCode(), e.getResponseBodyAsString());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "客户端请求错误: " + e.getStatusText());
    }

    /**
     * 处理 HTTP 服务端异常
     *
     * @param e 捕获到的 {@link org.springframework.web.client.HttpServerErrorException} 异常对象
     * @return 封装的响应结果，包含 HTTP 服务端错误信息
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public R<Void> handleHttpServerErrorException(HttpServerErrorException e) {
        log.error("HTTP 服务端异常: 状态码 {}, 错误信息 {}", e.getStatusCode(), e.getResponseBodyAsString());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "服务端处理失败，请稍后再试");
    }

    /**
     * 处理远程服务调用异常
     *
     * @param e 捕获到的 {@link org.springframework.web.client.RestClientException} 异常对象
     * @return 封装的响应结果，包含远程服务错误信息
     */
    @ExceptionHandler(RestClientException.class)
    public R<Void> handleRestClientException(RestClientException e) {
        log.error("远程服务调用失败", e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "远程服务调用失败，请稍后再试");
    }

    /**
     * IllegalArgumentException 异常捕获，主要用于 Assert
     *
     * @param e the e
     * @return 封装的响应结果，包含远程服务错误信息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R<Void> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error(String.format("请求地址: %s, 非法数据输入 ex=%s", request.getRequestURI(), e.getMessage()), e);
        return R.failed(HttpStatus.INVALID_ARGUMENT, String.format("非法数据输入:[%s],请求地址:[%s]", e.getMessage(), request.getRequestURI()));
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