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
import com.mobaijun.common.result.ErrorDataInfo;
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
import org.springframework.dao.DataIntegrityViolationException;
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
     * </p>
     *
     * @param e       捕获到的 {@link HttpRequestMethodNotSupportedException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<ErrorDataInfo> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        // 获取不支持的方法及支持的方法列表
        String unsupportedMethod = e.getMethod();
        String[] supportedMethods = e.getSupportedMethods();
        String supportedMethodsInfo = (supportedMethods != null && supportedMethods.length > 0) ? String.join(", ", supportedMethods) : "无可用方法";

        log.error("请求方法 '{}' 不被支持，支持的方法：{}", unsupportedMethod, supportedMethodsInfo);

        // 错误信息
        String errorMessage = String.format("请求方法 '%s' 不被支持。请使用以下方法：[%s]", unsupportedMethod, supportedMethodsInfo);

        // 使用通用方法来构建返回结果
        return handleException(e, request, HttpStatus.METHOD_NOT_ALLOWED, errorMessage, supportedMethodsInfo);
    }

    /**
     * 处理请求路径中缺少必需的路径变量异常
     * <p>
     * 当请求路径缺少必需的路径变量时，Spring 会抛出 {@link MissingPathVariableException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 400（Bad Request）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link MissingPathVariableException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public R<ErrorDataInfo> handleMissingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        // 获取路径和缺少的变量名
        String missingVariable = e.getVariableName();

        log.error("请求路径中缺少必需的路径变量：{}", missingVariable);
        // 构造错误信息
        String errorMessage = String.format("请求路径中缺少必需的路径变量：[%s]", missingVariable);

        // 使用通用的异常处理方法
        return handleException(e, request, HttpStatus.BAD_REQUEST, errorMessage, "");
    }

    /**
     * 处理请求参数类型不匹配异常
     * <p>
     * 当请求参数的类型与方法要求的类型不匹配时，Spring 会抛出 {@link MethodArgumentTypeMismatchException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 400（Invalid Argument）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link MethodArgumentTypeMismatchException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<ErrorDataInfo> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        // 获取不匹配的参数名、期望的类型和输入的值
        String parameterName = e.getName();
        String requiredType = Objects.requireNonNull(e.getRequiredType()).getName();
        Object invalidValue = e.getValue();

        // 构造错误信息
        String errorMessage = String.format("请求参数类型不匹配，参数 [%s] 要求类型为：'%s'，但输入值为：'%s'", parameterName, requiredType, invalidValue);

        // 记录日志
        log.error("请求路径 '{}' 的参数 '{}' 类型不匹配，期望类型：'{}'，但输入值为：'{}'", request.getRequestURI(), parameterName, requiredType, invalidValue, e);

        // 使用 handleException 返回标准化响应
        return handleException(e, request, HttpStatus.BAD_REQUEST, errorMessage, "");
    }

    /**
     * 处理找不到路由异常
     * <p>
     * 当请求的路由在 Spring 中不存在时，Spring 会抛出 {@link NoHandlerFoundException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 404（Not Found）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link NoHandlerFoundException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public R<ErrorDataInfo> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 不存在", request.getRequestURI(), e);
        return handleException(e, request, HttpStatus.NOT_FOUND, "请求的资源不存在，请检查请求路径!", "");
    }

    /**
     * 处理访问拒绝异常
     * <p>
     * 当用户尝试访问其没有权限的资源时，Spring 会抛出 {@link AccessDeniedException} 异常。
     * 本方法捕获此异常，记录相关信息，并返回状态码 403（Forbidden）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link AccessDeniedException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    public R<ErrorDataInfo> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 无权限访问", request.getRequestURI(), e);
        return handleException(e, request, HttpStatus.FORBIDDEN, "无权限访问该资源！", "");
    }

    /**
     * 处理文件上传大小超限异常
     * <p>
     * 当上传的文件大小超过配置的限制时，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 413（PAYLOAD_TOO_LARGE）和提示信息。
     * </p>
     *
     * @param e 捕获到的 {@link org.springframework.web.multipart.MaxUploadSizeExceededException} 异常对象
     * @return 标准化的响应结果，包含文件大小超限的错误提示
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE)
    public R<ErrorDataInfo> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.error("文件上传大小超限，最大允许上传大小为：{} 字节，错误信息：{}", e.getMaxUploadSize(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.PAYLOAD_TOO_LARGE, String.format("文件上传大小超限，最大允许上传大小为：%d 字节!", e.getMaxUploadSize()), "");
    }

    /**
     * 处理文件读取异常
     * <p>
     * 当文件读取或操作过程中发生 I/O 错误时，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e 捕获到的 {@link java.io.IOException} 异常对象
     * @return 标准化的响应结果，包含文件读取失败的错误提示
     */
    @ExceptionHandler(IOException.class)
    public R<ErrorDataInfo> handleIOException(IOException e, HttpServletRequest request) {
        log.error("文件操作失败，错误信息：{}", e.getMessage(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "文件操作失败，请稍后重试！", "");
    }

    /**
     * 处理未知的运行时异常
     * <p>
     * 当程序运行时出现未预期的运行时异常（例如 NullPointerException 等）时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link RuntimeException} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(RuntimeException.class)
    public R<ErrorDataInfo> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生未知运行时异常", request.getRequestURI(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "系统运行时出现错误，请稍后再试！", "");
    }

    /**
     * 处理系统异常
     * <p>
     * 当系统运行时出现未捕获的通用异常时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link Exception} 异常对象
     * @param request 当前的 {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(Exception.class)
    public R<ErrorDataInfo> handleException(Exception e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生系统异常，异常信息：{}", request.getRequestURI(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "系统内部错误，请稍后再试!", "");
    }

    /**
     * 处理自定义验证异常（绑定异常）
     * <p>
     * 当请求参数绑定失败时，Spring 会抛出 {@link BindException} 异常。
     * 本方法捕获此异常，记录所有验证错误信息，并返回封装的响应结果。
     * </p>
     *
     * @param e 捕获到的 {@link BindException} 异常对象
     * @return 封装的响应结果，包含所有验证错误信息
     */
    @ExceptionHandler(BindException.class)
    public R<ErrorDataInfo> handleBindException(BindException e, HttpServletRequest request) {
        String message = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
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
     * @param e 捕获到的 {@link ConstraintViolationException} 异常对象
     * @return 封装的响应结果，包含所有约束违规错误信息
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<ErrorDataInfo> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
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
     * @param e 捕获到的 {@link MethodArgumentNotValidException} 异常对象
     * @return 封装的响应结果，包含第一个字段的验证错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<ErrorDataInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("方法参数无效，验证失败，字段错误信息：{}", message);
        String errorMessage = String.format("方法参数无效，验证失败，字段错误信息：'%s'", message);
        return handleException(e, request, HttpStatus.BAD_REQUEST, errorMessage, "");
    }

    /**
     * 处理空指针异常
     * <p>
     * 当程序尝试访问空对象时，会抛出 {@link NullPointerException} 异常。
     * 本方法捕获此异常，记录请求地址和错误信息，并返回封装的响应结果。
     * </p>
     *
     * @param e       捕获到的 {@link NullPointerException} 异常对象
     * @param request 当前的 HTTP 请求对象
     * @return 封装的响应结果，包含 500 状态码和错误信息
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
    public R<ErrorDataInfo> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生空指针异常", request.getRequestURI(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "系统发生错误，请稍后重试，或联系管理员！", "");
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
    public R<ErrorDataInfo> handleServiceException(ServiceException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("业务异常发生，异常信息: {}, 请求路径: {}", e.getMessage(), request.getRequestURI());

        // 构建详细的错误信息，包含异常信息、请求路径、请求方法
        String errorMessage = String.format("系统发生异常，异常信息: {%s}", e.getMessage());

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
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
    public R<ErrorDataInfo> handleBaseException(BaseException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("基础异常发生，异常信息: {}, 请求路径: {}", e.getMessage(), request.getRequestURI(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "系统基础异常发生！", null);
    }

    /**
     * 处理缺失请求参数异常
     *
     * @param e 捕获到的 {@link MissingServletRequestParameterException} 异常对象
     * @return 封装的响应结果，包含缺失的请求参数信息
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<ErrorDataInfo> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        // 记录日志，包含缺失的请求参数信息
        log.error("请求缺少必需的请求参数 '{}', 请求路径: {}", e.getParameterName(), request.getRequestURI());

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.BAD_REQUEST, String.format("缺失请求参数: [%s]", e.getParameterName()), null);
    }

    /**
     * 处理不支持的媒体类型异常
     *
     * @param e 捕获到的 {@link HttpMediaTypeNotSupportedException} 异常对象
     * @return 封装的响应结果，包含不支持的媒体类型信息
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<ErrorDataInfo> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        // 记录日志，包含不支持的媒体类型信息
        log.error("不支持的媒体类型: {}, 请求路径: {}", e.getContentType(), request.getRequestURI());

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.UNSUPPORTED_MEDIA_TYPE, String.format("不支持的媒体类型: %s", e.getContentType()), null);
    }

    /**
     * 处理找不到元素异常
     *
     * @param e 捕获到的 {@link NoSuchElementException} 异常对象
     * @return 封装的响应结果，包含找不到的元素信息
     */
    @ExceptionHandler(NoSuchElementException.class)
    public R<ErrorDataInfo> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("请求的资源在数据库中不存在: {}, 请求路径: {}", e.getMessage(), request.getRequestURI());

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.NOT_FOUND, "请求的资源不存在！", null);
    }

    /**
     * 处理 SQL 异常，捕获数据库操作执行中的错误。
     * <p>
     * 当 SQL 执行异常（如查询、插入、更新操作等）发生时，本方法捕获 {@link SQLException} 异常，记录错误信息、
     * 请求路径和请求方法，并返回标准化的错误响应。通常此类异常会由于数据库连接问题、SQL 语法错误等原因触发。
     * </p>
     *
     * @param e       捕获到的 {@link SQLException} 异常对象，包含 SQL 执行错误的具体信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含 SQL 错误信息及相关细节
     */
    @ExceptionHandler(SQLException.class)
    public R<ErrorDataInfo> handleSQLException(SQLException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("SQL 执行异常: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "SQL 执行异常！", null);
    }

    /**
     * 处理数据完整性异常，捕获违反数据库唯一约束或完整性约束的错误。
     * <p>
     * 当操作违反数据库约束（如插入重复数据）时，本方法捕获 {@link DataIntegrityViolationException} 异常，
     * 记录错误信息、请求路径和请求方法，并返回相应的错误响应。常见的情况如插入重复数据或违反其他约束。
     * </p>
     *
     * @param e       捕获到的 {@link DataIntegrityViolationException} 异常对象，包含违反数据完整性约束的具体信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含详细的错误信息
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<ErrorDataInfo> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("数据库操作失败，违反唯一性或完整性约束: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.BAD_REQUEST, "操作失败，数据违反唯一性或完整性约束！", null);
    }

    /**
     * 处理空结果异常，捕获查询结果为空的情况。
     * <p>
     * 当执行查询操作时，如果没有找到符合条件的结果，本方法会捕获 {@link EmptyResultDataAccessException} 异常，
     * 记录相关错误信息、请求路径和请求方法，并返回资源未找到的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link EmptyResultDataAccessException} 异常对象，包含查询无结果的错误信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示资源未找到
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public R<ErrorDataInfo> handleEmptyResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("查询的资源不存在: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.NOT_FOUND, "查询的资源不存在！", null);
    }

    /**
     * 处理数据访问资源失败异常，捕获数据库连接失败或资源不可用的错误。
     * <p>
     * 当数据库访问出现资源不可用或连接问题时，本方法捕获 {@link DataAccessResourceFailureException} 异常，
     * 记录详细错误信息，并返回数据库服务不可用的错误响应。常见情况如数据库服务器停机或网络连接问题。
     * </p>
     *
     * @param e       捕获到的 {@link DataAccessResourceFailureException} 异常对象，包含数据库访问失败的具体信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含数据库连接失败的提示信息
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public R<ErrorDataInfo> handleDataAccessResourceFailureException(DataAccessResourceFailureException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("数据库访问资源失败: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "数据库访问资源失败！", null);
    }

    /**
     * 处理乐观锁异常，捕获数据冲突导致的乐观锁失败。
     * <p>
     * 当在并发环境下，多个请求试图修改相同数据时，可能会出现乐观锁失败的情况，
     * 本方法捕获 {@link OptimisticLockingFailureException} 异常，记录错误信息，并返回乐观锁失败的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link OptimisticLockingFailureException} 异常对象，包含乐观锁失败的详细信息
     * @param request 当前的 HTTP 请求对象，用于记录请求路径和方法
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示乐观锁失败
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public R<ErrorDataInfo> handleOptimisticLockingFailureException(OptimisticLockingFailureException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("乐观锁失败: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "乐观锁失败！", null);
    }

    /**
     * 处理查询结果大小不匹配异常，捕获查询结果数量不符合预期的情况。
     * <p>
     * 当数据库查询结果的数量与预期不符时，触发该异常（例如，期望单条记录返回，却返回了多条或零条记录）。
     * 本方法会捕获 {@link IncorrectResultSizeDataAccessException} 异常，记录错误信息，并返回标准化的错误响应。
     * </p>
     *
     * @param e 捕获到的 {@link IncorrectResultSizeDataAccessException} 异常对象，包含查询结果数量不匹配的详细信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示查询结果数量不匹配
     */
    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public R<ErrorDataInfo> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("查询结果数量异常: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "查询结果数量异常！", null);
    }

    /**
     * 处理网络请求超时异常，捕获因请求超时而导致的连接问题。
     * <p>
     * 当网络请求因超时未能在预定时间内完成时，本方法会捕获 {@link java.net.SocketTimeoutException} 异常，记录错误信息，
     * 并返回网络请求超时的错误响应。此异常通常发生在远程服务无法及时响应时。
     * </p>
     *
     * @param e 捕获到的 {@link java.net.SocketTimeoutException} 异常对象，包含网络超时的具体信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示网络请求超时
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public R<ErrorDataInfo> handleSocketTimeoutException(SocketTimeoutException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("网络请求超时: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.GATEWAY_TIMEOUT, "网络请求超时！", null);
    }

    /**
     * 处理网络连接失败异常，捕获无法建立连接时的错误。
     * <p>
     * 当无法建立与远程服务器的网络连接时（例如服务不可达或网络故障），本方法会捕获 {@link java.net.ConnectException} 异常，
     * 记录详细错误信息，并返回连接失败的错误响应。
     * </p>
     *
     * @param e 捕获到的 {@link java.net.ConnectException} 异常对象，包含连接失败的详细信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示网络连接失败
     */
    @ExceptionHandler(ConnectException.class)
    public R<ErrorDataInfo> handleConnectException(ConnectException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("网络连接失败: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "网络连接失败！", null);
    }

    /**
     * 处理未知主机异常，捕获因无法解析主机地址导致的连接错误。
     * <p>
     * 当程序尝试连接到一个无法解析的主机时（例如域名解析失败），本方法会捕获 {@link java.net.UnknownHostException} 异常，
     * 记录错误信息，并返回无法解析主机地址的错误响应。
     * </p>
     *
     * @param e 捕获到的 {@link java.net.UnknownHostException} 异常对象，包含无法解析主机地址的详细信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示无法解析主机地址
     */
    @ExceptionHandler(UnknownHostException.class)
    public R<ErrorDataInfo> handleUnknownHostException(UnknownHostException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("未知主机: {}, 请求路径: {}, 请求方法: {}", e.getMessage(), request.getRequestURI(), request.getMethod(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "未知主机错误！", null);
    }

    /**
     * 处理 HTTP 客户端异常，捕获由客户端请求错误引发的 HTTP 异常。
     * <p>
     * 当客户端请求的参数或格式不正确时（例如错误的 URL 或请求体），本方法会捕获 {@link org.springframework.web.client.HttpClientErrorException} 异常，
     * 记录 HTTP 状态码及错误信息，并返回客户端请求错误的响应。
     * </p>
     *
     * @param e 捕获到的 {@link org.springframework.web.client.HttpClientErrorException} 异常对象，包含 HTTP 客户端错误的详细信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示客户端请求错误
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public R<ErrorDataInfo> handleHttpClientErrorException(HttpClientErrorException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("HTTP 客户端异常: 状态码 {}, 错误信息 {}, 请求路径: {}, 请求方法: {}", e.getStatusCode(), e.getResponseBodyAsString(), request.getRequestURI(), request.getMethod(), e);

        // 构建详细的错误信息
        String errorMessage = String.format("HTTP 客户端异常: 状态码 {%s}, 错误信息 {%s}", e.getStatusCode(), e.getResponseBodyAsString());

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }

    /**
     * 处理 HTTP 服务端异常，捕获由服务端错误引发的 HTTP 异常。
     * <p>
     * 当远程服务发生错误时（例如服务端抛出 5xx 错误码），本方法会捕获 {@link org.springframework.web.client.HttpServerErrorException} 异常，
     * 记录 HTTP 状态码及错误信息，并返回服务端错误的响应。
     * </p>
     *
     * @param e 捕获到的 {@link org.springframework.web.client.HttpServerErrorException} 异常对象，包含 HTTP 服务端错误的详细信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示服务端处理失败
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public R<ErrorDataInfo> handleHttpServerErrorException(HttpServerErrorException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("HTTP 服务端异常: 状态码 {}, 错误信息 {}, 请求路径: {}, 请求方法: {}", e.getStatusCode(), e.getResponseBodyAsString(), request.getRequestURI(), request.getMethod(), e);

        // 构建详细的错误信息
        String errorMessage = String.format("HTTP 服务端异常: 状态码 {%s}, 错误信息 {%s}", e.getStatusCode(), e.getResponseBodyAsString());

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }

    /**
     * 处理远程服务调用异常
     * <p>
     * 当远程服务调用失败时，本方法会捕获 {@link org.springframework.web.client.RestClientException} 异常，记录错误信息，
     * 并返回远程服务调用失败的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link org.springframework.web.client.RestClientException} 异常对象，包含远程服务调用失败的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含远程服务错误信息
     */
    @ExceptionHandler(RestClientException.class)
    public R<ErrorDataInfo> handleRestClientException(RestClientException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("远程服务调用失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "远程服务调用失败，请稍后再试！", null);
    }

    /**
     * 处理非法参数异常，通常用于捕获 {@link IllegalArgumentException}
     * <p>
     * 当传入非法参数时（例如参数格式不正确、缺少必填字段等），本方法会捕获 {@link IllegalArgumentException} 异常，
     * 记录错误信息，并返回非法参数错误的响应。
     * </p>
     *
     * @param e       捕获到的 {@link IllegalArgumentException} 异常对象，包含非法参数的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含非法参数错误信息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R<ErrorDataInfo> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("非法数据输入, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.BAD_REQUEST, "非法数据输入，请检查输入数据！", null);
    }

    /**
     * 处理数据库约束违反的异常
     * <p>
     * 当数据库操作违反唯一约束（例如插入重复的唯一键值）时会抛出该异常。
     * 本方法会捕获 {@link SQLIntegrityConstraintViolationException} 异常，记录错误信息，
     * 并返回状态码 409（ALREADY_EXISTS）和提示信息，指示操作违反了数据库唯一约束。
     * </p>
     *
     * @param e       捕获到的 {@link SQLIntegrityConstraintViolationException} 异常对象，包含违反唯一约束的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含数据库约束错误信息
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<ErrorDataInfo> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("数据库约束违反, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.CONFLICT, "操作违反数据库唯一约束，请确认数据是否重复！", null);
    }

    /**
     * 处理数据库连接失败的异常
     * <p>
     * 当数据库连接无法建立或失效时会抛出该异常，例如数据库服务器不可达。
     * 本方法会捕获 {@link SQLNonTransientConnectionException} 异常，记录错误信息，
     * 并返回状态码 503（SERVICE_UNAVAILABLE）和提示信息，指示数据库连接失败。
     * </p>
     *
     * @param e       捕获到的 {@link SQLNonTransientConnectionException} 异常对象，包含数据库连接失败的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含数据库连接错误信息
     */
    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public R<ErrorDataInfo> handleSQLNonTransientConnectionException(SQLNonTransientConnectionException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("数据库连接失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "数据库连接失败，请稍后重试！", null);
    }

    /**
     * 处理数据库事务回滚的异常
     * <p>
     * 当数据库事务执行失败，导致回滚时会抛出该异常。
     * 本方法会捕获 {@link SQLTransactionRollbackException} 异常，记录错误信息，
     * 并返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息，指示事务回滚并请求重试。
     * </p>
     *
     * @param e       捕获到的 {@link SQLTransactionRollbackException} 异常对象，包含事务回滚的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含事务回滚错误信息
     */
    @ExceptionHandler(SQLTransactionRollbackException.class)
    public R<ErrorDataInfo> handleSQLTransactionRollbackException(SQLTransactionRollbackException e, HttpServletRequest request) {
        // 记录日志，包含异常信息和请求路径
        log.error("事务回滚, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);

        // 调用通用异常处理方法
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "操作失败，事务已回滚，请重试！", null);
    }

    /**
     * 处理批量更新失败的异常
     * <p>
     * 当批量更新操作失败时（例如批量插入或更新数据时出现错误）会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link BatchUpdateException} 异常对象，包含批量更新失败的详细信息
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BatchUpdateException.class)
    public R<ErrorDataInfo> handleBatchUpdateException(BatchUpdateException e, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        // 打印详细日志，包括请求地址、错误信息、堆栈信息
        log.error("批量更新失败, 请求地址: {}, 错误信息: {} ", request.getRequestURI(), errorMessage);

        // 返回详细的错误响应
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "批量更新操作失败，可能由于数据格式不符合要求或数据库连接问题！", null);
    }

    /**
     * 处理 SQL 语法错误的异常
     * <p>
     * 当执行 SQL 语法错误时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link BadSqlGrammarException} 异常对象，包含 SQL 语法错误的详细信息
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public R<ErrorDataInfo> handleBadSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        // 打印详细日志，包括请求地址、错误信息、堆栈信息
        log.error("SQL 语法错误, 请求地址: {}, 错误信息: {}", request.getRequestURI(), errorMessage);

        // 返回详细的错误响应
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "SQL 语法错误，可能由于 SQL 语句编写不规范，请检查输入的 SQL 语句！", null);
    }

    /**
     * 处理通用数据访问异常
     * <p>
     * 处理其他 MyBatis 或数据库访问操作失败时抛出的异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link DataAccessException} 异常对象，包含数据库访问失败的详细信息
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(DataAccessException.class)
    public R<ErrorDataInfo> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        // 打印详细日志，包括请求地址、错误信息、堆栈信息
        log.error("数据库操作失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), errorMessage);

        // 返回详细的错误响应
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "数据库操作失败，可能由于数据库连接问题或查询语句问题，请稍后重试！", null);
    }

    /**
     * 处理请求体不可读异常
     * <p>
     * 当请求体无法被解析时，Spring 会抛出 {@link HttpMessageNotReadableException} 异常。
     * 本方法捕获此异常，并记录详细的错误信息，包括请求地址和解析错误的提示。
     * </p>
     *
     * @param e       捕获到的 {@link HttpMessageNotReadableException} 异常对象，包含请求体解析失败的详细信息
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的 {@link R<Void>} 响应结果，包含错误提示信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<ErrorDataInfo> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        // 打印详细日志，包括请求地址、错误信息、堆栈信息
        log.error("请求参数不可用, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());

        // 返回详细的错误响应
        return handleException(e, request, HttpStatus.BAD_REQUEST, "请求参数不可用，可能由于请求体格式不正确或数据不完整，请检查输入！", null);
    }

    /**
     * 处理 MyBatis 批量操作失败的异常
     * <p>
     * 当执行 MyBatis 批量操作时出现错误（例如 SQL 语句错误）会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link org.apache.ibatis.executor.BatchExecutorException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(BatchExecutorException.class)
    public R<ErrorDataInfo> handleBatchExecutorException(BatchExecutorException e, HttpServletRequest request) {
        // 打印详细日志
        log.error("批量执行失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage());

        // 返回详细错误信息
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "批量操作执行失败，可能由于 SQL 语句错误或数据格式问题，请稍后重试！", null);
    }

    /**
     * 处理 MyBatis 持久化操作失败的异常
     * <p>
     * 当 MyBatis 执行持久化操作（如插入、更新、删除、查询）失败时会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       {@link org.apache.ibatis.exceptions.PersistenceException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(PersistenceException.class)
    public R<ErrorDataInfo> handlePersistenceException(PersistenceException e, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        // 打印详细日志
        log.error("持久化操作失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), errorMessage);

        // 返回详细错误信息
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "持久化操作失败，可能由于数据库连接错误或数据问题，请稍后重试！", null);
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
        String errorMessage = e.getMessage();
        // 打印详细日志
        log.error("MyBatis 配置文件或映射文件构建失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), errorMessage);

        // 返回详细错误信息
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "SQL 构建失败，可能由于配置文件错误或映射问题，请联系开发人员！", null);
    }

    /**
     * 处理 Servlet 异常
     * <p>
     * 捕获 ServletException 异常，并根据异常信息返回不同的错误响应。
     * </p>
     *
     * @param e       {@link ServletException} 异常对象
     * @param request {@link HttpServletRequest} 对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含错误提示和请求的接口地址
     */
    @ExceptionHandler(ServletException.class)
    public R<ErrorDataInfo> handleServletException(ServletException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = e.getMessage();
        // 认证失败日志
        if (errorMessage != null && errorMessage.contains("NotLoginException")) {
            log.error("请求地址'{}', 认证失败'{}', 无法访问系统资源", requestURI, errorMessage);
            return handleException(e, request, HttpStatus.UNAUTHORIZED, "认证失败，无法访问系统资源！", null);
        } else {
            // 打印详细日志
            log.error("请求地址'{}', 发生未知异常: {}", requestURI, errorMessage, e);
            return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "系统内部错误，请联系管理员！", null);
        }
    }

    /**
     * 通用异常处理方法
     * <p>
     * 该方法封装了错误信息构建、日志记录和返回结果的标准流程，减少了重复代码。
     * </p>
     *
     * @param exception    异常对象
     * @param request      当前请求对象
     * @param httpStatus   HTTP 状态码
     * @param errorMessage 错误信息
     * @param methodsInfo  支持的方法列表信息（可选）
     * @return 标准化的响应结果
     */
    private R<ErrorDataInfo> handleException(Exception exception, HttpServletRequest request, HttpStatus httpStatus, String errorMessage, String methodsInfo) {
        // 构造错误数据信息对象
        ErrorDataInfo errorData = ErrorDataInfo.builder()
                // 请求路径
                .path(request.getRequestURI())
                // 异常类型
                .exceptionType(exception.getClass().getSimpleName())
                // 请求方法
                .method(request.getMethod())
                // 支持方法列表
                .methods(methodsInfo != null && !methodsInfo.isBlank() ? methodsInfo : "")
                // 错误详情
                .errorInfo(errorMessage)
                .build();
        // 返回封装的 R<ErrorDataInfo> 响应对象
        return R.failed(httpStatus, errorData);
    }
}