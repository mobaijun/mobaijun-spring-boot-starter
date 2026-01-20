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
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

/**
 * Description: Spring Web Client 相关异常处理器
 * Author: [mobaijun]
 * Date: [2025/11/22]
 * <p>
 * 处理 Spring Web Client 相关的异常，仅在存在 Spring Web Client 依赖时生效。
 * 包括：
 * - HttpClientErrorException: HTTP 客户端错误（4xx）
 * - HttpServerErrorException: HTTP 服务端错误（5xx）
 * - RestClientException: Rest 客户端通用异常
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "org.springframework.web.client.RestClientException")
public class WebClientExceptionHandler extends BaseExceptionHandler {

    /**
     * 处理 HTTP 客户端异常，捕获由客户端请求错误引发的 HTTP 异常。
     * <p>
     * 当客户端请求的参数或格式不正确时（例如错误的 URL 或请求体），本方法会捕获 {@link HttpClientErrorException} 异常，
     * 记录 HTTP 状态码及错误信息，并返回客户端请求错误的响应。
     * </p>
     *
     * @param e       捕获到的 {@link HttpClientErrorException} 异常对象，包含 HTTP 客户端错误的详细信息
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示客户端请求错误
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public R<ErrorDataInfo> handleHttpClientErrorException(HttpClientErrorException e, HttpServletRequest request) {
        log.error("HTTP 客户端异常: 状态码 {}, 错误信息 {}, 请求路径: {}, 请求方法: {}", 
                e.getStatusCode(), e.getResponseBodyAsString(), request.getRequestURI(), request.getMethod(), e);
        String errorMessage = String.format("HTTP 客户端异常: 状态码 {%s}, 错误信息 {%s}", 
                e.getStatusCode(), e.getResponseBodyAsString());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }

    /**
     * 处理 HTTP 服务端异常，捕获由服务端错误引发的 HTTP 异常。
     * <p>
     * 当远程服务发生错误时（例如服务端抛出 5xx 错误码），本方法会捕获 {@link HttpServerErrorException} 异常，
     * 记录 HTTP 状态码及错误信息，并返回服务端错误的响应。
     * </p>
     *
     * @param e       捕获到的 {@link HttpServerErrorException} 异常对象，包含 HTTP 服务端错误的详细信息
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示服务端处理失败
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public R<ErrorDataInfo> handleHttpServerErrorException(HttpServerErrorException e, HttpServletRequest request) {
        log.error("HTTP 服务端异常: 状态码 {}, 错误信息 {}, 请求路径: {}, 请求方法: {}", 
                e.getStatusCode(), e.getResponseBodyAsString(), request.getRequestURI(), request.getMethod(), e);
        String errorMessage = String.format("HTTP 服务端异常: 状态码 {%s}, 错误信息 {%s}", 
                e.getStatusCode(), e.getResponseBodyAsString());
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, null);
    }

    /**
     * 处理远程服务调用异常
     * <p>
     * 当远程服务调用失败时，本方法会捕获 {@link RestClientException} 异常，记录错误信息，
     * 并返回远程服务调用失败的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link RestClientException} 异常对象，包含远程服务调用失败的详细信息
     * @param request 当前 HTTP 请求对象，用于获取请求地址
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，包含远程服务错误信息
     */
    @ExceptionHandler(RestClientException.class)
    public R<ErrorDataInfo> handleRestClientException(RestClientException e, HttpServletRequest request) {
        log.error("远程服务调用失败, 请求地址: {}, 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "远程服务调用失败，请稍后再试！", null);
    }
}