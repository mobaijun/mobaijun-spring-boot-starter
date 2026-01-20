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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Description: 网络相关异常处理器
 * Author: [mobaijun]
 * Date: [2025/11/22]
 * <p>
 * 处理网络连接相关的异常，包括：
 * - SocketTimeoutException: 网络请求超时
 * - ConnectException: 网络连接失败
 * - UnknownHostException: 未知主机错误
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "java.net.SocketTimeoutException")
public class NetworkExceptionHandler extends BaseExceptionHandler {

    /**
     * 处理网络请求超时异常，捕获因请求超时而导致的连接问题。
     * <p>
     * 当网络请求因超时未能在预定时间内完成时，本方法会捕获 {@link SocketTimeoutException} 异常，记录错误信息，
     * 并返回网络请求超时的错误响应。此异常通常发生在远程服务无法及时响应时。
     * </p>
     *
     * @param e       捕获到的 {@link SocketTimeoutException} 异常对象，包含网络超时的具体信息
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示网络请求超时
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public R<ErrorDataInfo> handleSocketTimeoutException(SocketTimeoutException e, HttpServletRequest request) {
        log.error("网络请求超时: {}, 请求路径: {}, 请求方法: {}", 
                e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.GATEWAY_TIMEOUT, "网络请求超时！", null);
    }

    /**
     * 处理网络连接失败异常，捕获无法建立连接时的错误。
     * <p>
     * 当无法建立与远程服务器的网络连接时（例如服务不可达或网络故障），本方法会捕获 {@link ConnectException} 异常，
     * 记录详细错误信息，并返回连接失败的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link ConnectException} 异常对象，包含连接失败的详细信息
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示网络连接失败
     */
    @ExceptionHandler(ConnectException.class)
    public R<ErrorDataInfo> handleConnectException(ConnectException e, HttpServletRequest request) {
        log.error("网络连接失败: {}, 请求路径: {}, 请求方法: {}", 
                e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "网络连接失败！", null);
    }

    /**
     * 处理未知主机异常，捕获因无法解析主机地址导致的连接错误。
     * <p>
     * 当程序尝试连接到一个无法解析的主机时（例如域名解析失败），本方法会捕获 {@link UnknownHostException} 异常，
     * 记录错误信息，并返回无法解析主机地址的错误响应。
     * </p>
     *
     * @param e       捕获到的 {@link UnknownHostException} 异常对象，包含无法解析主机地址的详细信息
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 封装的 {@link R<ErrorDataInfo>} 响应结果，提示无法解析主机地址
     */
    @ExceptionHandler(UnknownHostException.class)
    public R<ErrorDataInfo> handleUnknownHostException(UnknownHostException e, HttpServletRequest request) {
        log.error("未知主机: {}, 请求路径: {}, 请求方法: {}", 
                e.getMessage(), request.getRequestURI(), request.getMethod(), e);
        return handleException(e, request, HttpStatus.SERVICE_UNAVAILABLE, "未知主机错误！", null);
    }
}