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
import com.mobaijun.common.exception.ServiceException;
import com.mobaijun.common.exception.base.BaseException;
import com.mobaijun.common.result.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTransactionRollbackException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchExecutorException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
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
 * Description: 全局异常处理器，用于捕获并处理常见异常，并返回标准化的响应结果。
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
     * 本方法捕获此异常，记录相关信息，并返回状态码 405（Method Not Allowed）和提示信息。
     *
     * @param e       捕获到的 {@link HttpRequestMethodNotSupportedException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 不支持 '{}' 请求方法", request.getRequestURI(), e.getMethod(), e);
        return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "不支持该请求方法，请检查并使用正确的 HTTP 方法");
    }

    /**
     * 处理请求路径中缺少必需的路径变量异常
     * <p>
     * 当请求路径缺少必需的路径变量时，Spring 会抛出 {@link MissingPathVariableException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 400（Invalid Argument）和提示信息。
     *
     * @param e       捕获到的 {@link MissingPathVariableException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public R<Void> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        log.error("请求路径 '{}' 中缺少必需的路径变量 '{}'，发生系统异常.", request.getRequestURI(), e.getVariableName(), e);
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, String.format("请求路径中缺少必需的路径变量 [%s]", e.getVariableName()));
    }

    /**
     * 处理请求参数类型不匹配异常
     * <p>
     * 当请求参数的类型与方法要求的类型不匹配时，Spring 会抛出 {@link MethodArgumentTypeMismatchException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 400（Invalid Argument）和提示信息。
     *
     * @param e       捕获到的 {@link MethodArgumentTypeMismatchException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("请求路径 '{}' 的参数 '{}' 类型不匹配，期望类型：'{}'，但输入值为：'{}'", request.getRequestURI(), e.getName(), Objects.requireNonNull(e.getRequiredType()).getName(), e.getValue(), e);
        String errorMessage = String.format("请求参数类型不匹配，参数 [%s] 要求类型为：'%s'，但输入值为：'%s'", e.getName(), Objects.requireNonNull(e.getRequiredType()).getName(), e.getValue());
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, errorMessage);
    }

    /**
     * 处理找不到路由异常
     * <p>
     * 当请求的路由在 Spring 中不存在时，Spring 会抛出 {@link NoHandlerFoundException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 404（Not Found）和提示信息。
     *
     * @param e       捕获到的 {@link NoHandlerFoundException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public R<Void> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 不存在", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.NOT_FOUND, "请求的资源不存在，请检查请求路径");
    }

    /**
     * 处理访问拒绝异常
     * <p>
     * 当用户尝试访问其没有权限的资源时，Spring 会抛出 {@link AccessDeniedException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 403（Forbidden）和提示信息。
     *
     * @param e       捕获到的 {@link AccessDeniedException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    public R<Void> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 无权限访问", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.PERMISSION_DENIED, "无权限访问该资源");
    }

    /**
     * 处理文件上传大小超限异常
     * <p>
     * 当上传的文件大小超过配置的限制时，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 413（PAYLOAD_TOO_LARGE）和提示信息。
     *
     * @param e 捕获到的 {@link org.springframework.web.multipart.MaxUploadSizeExceededException} 异常对象
     * @return 标准化的响应结果，包含文件大小超限的错误提示
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE)
    public R<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件上传大小超限，最大允许上传大小为：{} 字节，错误信息：{}", e.getMaxUploadSize(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE, "文件大小超限，请上传符合大小限制的文件");
    }

    /**
     * 处理文件读取异常
     * <p>
     * 当文件读取或操作过程中发生 I/O 错误时，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e 捕获到的 {@link java.io.IOException} 异常对象
     * @return 标准化的响应结果，包含文件读取失败的错误提示
     */
    @ExceptionHandler(IOException.class)
    public R<Void> handleIOException(IOException e) {
        log.error("文件操作失败，错误信息：{}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "文件操作失败，请检查文件是否可用");
    }

    /**
     * 处理未知的运行时异常
     * <p>
     * 当程序运行时出现未预期的运行时异常（例如 NullPointerException 等）时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       捕获到的 {@link RuntimeException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(RuntimeException.class)
    public R<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生未知运行时异常，异常信息：{}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "系统运行时出现错误，请稍后再试");
    }

    /**
     * 处理系统异常
     * <p>
     * 当系统运行时出现未捕获的通用异常时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       捕获到的 {@link Exception} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生系统异常，异常信息：{}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "系统内部错误，请稍后再试");
    }

    /**
     * 处理自定义验证异常（绑定异常）
     * <p>
     * 当请求参数绑定失败时，Spring 会抛出 {@link BindException} 异常。
     * 本方法捕获此异常，记录所有验证错误信息，并返回封装的响应结果。
     *
     * @param e 捕获到的 {@link BindException} 异常对象
     * @return 封装的响应结果，包含所有验证错误信息
     */
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(BindException e) {
        log.error("参数绑定异常", e);
        String message = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, message);
    }

    /**
     * 处理自定义验证异常（约束违规异常）
     * <p>
     * 当请求参数违反约束条件时，Spring 会抛出 {@link ConstraintViolationException} 异常。
     * 本方法捕获此异常，记录所有约束违规错误信息，并返回封装的响应结果。
     *
     * @param e 捕获到的 {@link ConstraintViolationException} 异常对象
     * @return 封装的响应结果，包含所有约束违规错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("约束违规异常", e);
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, message);
    }

    /**
     * 处理自定义验证异常（方法参数无效异常）
     * <p>
     * 当方法参数验证失败时，Spring 会抛出 {@link MethodArgumentNotValidException} 异常。
     * 本方法捕获此异常，记录第一个字段的验证错误信息，并返回封装的响应结果。
     *
     * @param e 捕获到的 {@link MethodArgumentNotValidException} 异常对象
     * @return 封装的响应结果，包含第一个字段的验证错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("方法参数无效异常", e);
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, message);
    }

    /**
     * 处理空指针异常
     * <p>
     * 当程序尝试访问空对象时，会抛出 {@link NullPointerException} 异常。
     * 本方法捕获此异常，记录请求地址和错误信息，并返回封装的响应结果。
     *
     * @param e       捕获到的 {@link NullPointerException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含 500 状态码和错误信息
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生空指针异常", request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("系统发生空指针异常，请检查参数后重新尝试! 请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理业务异常
     * <p>
     * 当业务逻辑出现错误时，会抛出 {@link com.mobaijun.common.exception.ServiceException} 异常。
     * 本方法捕获此异常，记录请求路径和错误信息，并返回封装的响应结果。
     *
     * @param e       捕获到的业务异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含错误码和错误信息
     */
    @ExceptionHandler(ServiceException.class)
    public R<Void> handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error("业务异常发生，异常信息: {}, 请求路径: {}", e.getMessage(), request.getRequestURI());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 处理基础异常
     * <p>
     * 当捕获到基础异常时，Spring 会调用本方法进行处理。
     * 本方法记录异常信息和请求路径，并返回封装的响应结果。
     *
     * @param e       捕获到的 {@link com.mobaijun.common.exception.base.BaseException } 基础异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含错误信息
     */
    @ExceptionHandler(BaseException.class)
    public R<Void> handleBaseException(BaseException e, HttpServletRequest request) {
        log.error("基础异常发生，异常信息: {}, 请求路径: {}", e.getMessage(), request.getRequestURI(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("系统发生错误{ %s }，请稍后再试!", e.getMessage()));
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
     * @param e 捕获到的 {@link DuplicateKeyException} 异常对象
     * @return 封装的响应结果，包含详细的错误信息
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Void> handleDataIntegrityViolationException(DuplicateKeyException e) {
        log.error("数据完整性违规: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.PERMANENT_REDIRECT, "操作失败，违反数据唯一约束！");
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
     * 处理网络连接失败异常
     *
     * @param e 捕获到的 {@link java.net.ConnectException} 异常对象
     * @return 封装的响应结果，包含网络连接错误信息
     */
    @ExceptionHandler(ConnectException.class)
    public R<Void> handleConnectException(ConnectException e) {
        log.error("网络连接失败: {}", e.getMessage(), e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "无法连接到服务，请稍后再试");
    }

    /**
     * 处理主机未知异常
     *
     * @param e 捕获到的 {@link java.net.UnknownHostException} 异常对象
     * @return 封装的响应结果，包含主机未知错误信息
     */
    @ExceptionHandler(UnknownHostException.class)
    public R<Void> handleUnknownHostException(UnknownHostException e) {
        log.error("未知主机: {}", e.getMessage(), e);
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
        log.error("HTTP 客户端异常: 状态码 {}, 错误信息 {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
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
        log.error("HTTP 服务端异常: 状态码 {}, 错误信息 {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "服务端处理失败，请稍后再试");
    }

    /**
     * 处理远程服务调用异常
     *
     * @param e       捕获到的 {@link org.springframework.web.client.RestClientException} 异常对象
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的响应结果，包含远程服务错误信息
     */
    @ExceptionHandler(RestClientException.class)
    public R<Void> handleRestClientException(RestClientException e, HttpServletRequest request) {
        log.error("远程服务调用失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, String.format("远程服务调用失败，请稍后再试, 请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理非法参数异常，通常用于捕获 {@link IllegalArgumentException}
     *
     * @param e       捕获到的 {@link IllegalArgumentException} 异常对象
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的响应结果，包含非法参数错误信息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R<Void> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("非法数据输入, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, String.format("非法数据输入，请检查输入数据。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理数据库约束违反的异常
     * <p>
     * 当数据库操作违反唯一约束（例如插入重复的唯一键值）时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 409（ALREADY_EXISTS）和提示信息。
     *
     * @param e       {@link SQLIntegrityConstraintViolationException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<Void> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        log.error("数据库约束违反, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.ALREADY_EXISTS, String.format("操作违反数据库唯一约束，请确认数据是否重复。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理数据库连接失败的异常
     * <p>
     * 当数据库连接无法建立或失效时会抛出该异常，例如数据库服务器不可达。
     * 该方法捕获异常并记录日志，返回状态码 503（SERVICE_UNAVAILABLE）和提示信息。
     *
     * @param e       {@link SQLNonTransientConnectionException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public R<Void> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException e, HttpServletRequest request) {
        log.error("数据库连接失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, String.format("数据库连接失败，请稍后重试。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理数据库事务回滚的异常
     * <p>
     * 当数据库事务执行失败，导致回滚时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link SQLTransactionRollbackException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(SQLTransactionRollbackException.class)
    public R<Void> handleSQLTransactionRollbackException(SQLTransactionRollbackException e, HttpServletRequest request) {
        log.error("事务回滚, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("操作失败，事务已回滚，请重试。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理批量更新失败的异常
     * <p>
     * 当批量更新操作失败时（例如批量插入或更新数据时出现错误）会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link BatchUpdateException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BatchUpdateException.class)
    public R<Void> handleBatchUpdateException(BatchUpdateException e, HttpServletRequest request) {
        log.error("批量更新失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("批量更新失败，请检查输入数据。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理 SQL 语法错误的异常
     * <p>
     * 当执行 SQL 语法错误时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link BadSqlGrammarException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public R<Void> handleBadSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        log.error("SQL 语法错误, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("SQL 语法错误，请稍后重试。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理通用数据访问异常
     * <p>
     * 处理其他 MyBatis 或数据库访问操作失败时抛出的异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link DataAccessException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(DataAccessException.class)
    public R<Void> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        log.error("数据库操作失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("数据库操作失败，请稍后重试。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理请求体不可读异常
     * <p>
     * 当请求体无法被解析时，Spring 会抛出 {@link HttpMessageNotReadableException} 异常。
     * 本方法捕获此异常，并记录详细的错误信息，包括请求地址和解析错误的提示。
     *
     * @param e       捕获到的 {@link HttpMessageNotReadableException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含错误提示
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("请求参数不可用, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INVALID_ARGUMENT, String.format("请求参数类型错误，请检查输入。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理 MyBatis 批量操作失败的异常
     * <p>
     * 当执行 MyBatis 批量操作时出现错误（例如 SQL 语句错误）会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link BatchExecutorException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BatchExecutorException.class)
    public R<Void> handleBatchExecutorException(BatchExecutorException e, HttpServletRequest request) {
        log.error("批量执行失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("批量操作执行失败，请稍后重试。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理 MyBatis 持久化操作失败的异常
     * <p>
     * 当 MyBatis 执行持久化操作（如插入、更新、删除、查询）失败时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link PersistenceException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(PersistenceException.class)
    public R<Void> handlePersistenceException(PersistenceException e, HttpServletRequest request) {
        log.error("持久化操作失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("持久化操作失败，请稍后重试。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理 MyBatis 构建配置文件或映射文件失败的异常
     * <p>
     * 当 MyBatis 构建配置文件或映射文件时出现错误，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     *
     * @param e       {@link BuilderException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BuilderException.class)
    public R<Void> handleBuilderException(BuilderException e, HttpServletRequest request) {
        log.error("MyBatis 配置文件或映射文件构建失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, String.format("SQL 构建失败，请联系开发人员。请求地址：{%s}", request.getRequestURI()));
    }

    /**
     * 处理 Servlet 异常
     * <p>
     * 捕获 ServletException 异常，并根据异常信息返回不同的错误响应。
     *
     * @param e       {@link ServletException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(ServletException.class)
    public R<Void> handleServletException(ServletException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = e.getMessage();
        if (errorMessage != null && errorMessage.contains("NotLoginException")) {
            log.error("请求地址'{}', 认证失败'{}', 无法访问系统资源", requestURI, errorMessage);
            return buildErrorResponse(HttpStatus.UNAUTHENTICATED, "认证失败，无法访问系统资源");
        } else {
            log.error("请求地址'{}', 发生未知异常: {}", requestURI, errorMessage, e);
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "系统内部错误，请联系管理员");
        }
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