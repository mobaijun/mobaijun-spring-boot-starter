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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

/**
 * Description: 文件上传相关异常处理器
 * Author: [mobaijun]
 * Date: [2025/11/22]
 * <p>
 * 处理文件上传和文件操作相关的异常，仅在存在 Spring Web Multipart 支持时生效。
 * 包括：
 * - MaxUploadSizeExceededException: 文件上传大小超限
 * - IOException: 文件操作失败
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnClass(name = "org.springframework.web.multipart.MaxUploadSizeExceededException")
public class FileUploadExceptionHandler extends BaseExceptionHandler {

    /**
     * 处理文件上传大小超限异常
     * <p>
     * 当上传的文件大小超过配置的限制时，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 413（PAYLOAD_TOO_LARGE）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link MaxUploadSizeExceededException} 异常对象
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含文件大小超限的错误提示
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE)
    public R<ErrorDataInfo> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.error("文件上传大小超限，最大允许上传大小为：{} 字节，错误信息：{}", 
                e.getMaxUploadSize(), e.getMessage(), e);
        String errorMessage = String.format("文件上传大小超限，最大允许上传大小为：%d 字节!", e.getMaxUploadSize());
        return handleException(e, request, HttpStatus.PAYLOAD_TOO_LARGE, errorMessage, "");
    }

    /**
     * 处理文件读取异常
     * <p>
     * 当文件读取或操作过程中发生 I/O 错误时，会抛出该异常。
     * 该方法捕获异常并记录日志，返回状态码 500（INTERNAL_SERVER_ERROR）和提示信息。
     * </p>
     *
     * @param e       捕获到的 {@link IOException} 异常对象
     * @param request 当前的 HTTP 请求对象，用于获取请求相关的信息
     * @return 标准化的响应结果，包含文件读取失败的错误提示
     */
    @ExceptionHandler(IOException.class)
    public R<ErrorDataInfo> handleIOException(IOException e, HttpServletRequest request) {
        log.error("文件操作失败，错误信息：{}", e.getMessage(), e);
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR, "文件操作失败，请稍后重试！", "");
    }
}