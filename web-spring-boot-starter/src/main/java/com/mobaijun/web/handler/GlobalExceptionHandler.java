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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Description: 全局异常处理器，用于捕获并处理常见异常，并返回标准化的响应结果。
 * Author: [mobaijun]
 * Date: [2024/8/13 18:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

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
    public R<ErrorDataInfo> handleGeneralException(Exception e, HttpServletRequest request) {
        log.error("请求地址 '{}' 发生系统异常，异常信息：{}", request.getRequestURI(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "系统内部错误，请稍后再试!", "");
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
        String errorMessage = String.format("%s", e.getMessage());

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
}