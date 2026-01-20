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

/**
 * Description: 异常处理器基类，提供通用的异常处理方法
 * Author: [mobaijun]
 * Date: [2024/8/13 18:06]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public abstract class BaseExceptionHandler {

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
    protected R<ErrorDataInfo> handleException(Exception exception, HttpServletRequest request, 
                                               HttpStatus httpStatus, String errorMessage, String methodsInfo) {
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
        return R.failed(errorData, httpStatus);
    }
}