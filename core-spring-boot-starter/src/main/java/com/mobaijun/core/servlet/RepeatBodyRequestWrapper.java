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
package com.mobaijun.core.servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

/**
 * Description: [Request包装类：允许 body 重复读取]
 * Author: [mobaijun]
 * Date: [2024/7/30 14:35]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class RepeatBodyRequestWrapper extends HttpServletRequestWrapper {

    private static final Log log = LogFactory.getLog(RepeatBodyRequestWrapper.class);

    /**
     * 请求体的字节数组
     */
    private final byte[] bodyByteArray;

    /**
     * 请求参数映射表
     */
    private final Map<String, String[]> parameterMap;

    /**
     * 构造方法，从原始请求中读取请求体并缓存
     *
     * @param request 原始 HttpServletRequest
     */
    public RepeatBodyRequestWrapper(HttpServletRequest request) {
        super(request);
        this.bodyByteArray = getByteBody(request);
        this.parameterMap = super.getParameterMap();
    }

    /**
     * 从 HttpServletRequest 中读取请求体并转换为字节数组
     *
     * @param request 原始 HttpServletRequest
     * @return 请求体的字节数组
     */
    private static byte[] getByteBody(HttpServletRequest request) {
        byte[] body = new byte[0];
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("解析流中数据异常", e);
        }
        return body;
    }

    /**
     * 获取 BufferedReader 对象，从缓存的请求体中读取数据
     *
     * @return BufferedReader 对象，如果请求体为空则返回 null
     */
    @Override
    public BufferedReader getReader() {
        return ObjectUtils.isEmpty(this.bodyByteArray) ? null
                : new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 获取 ServletInputStream 对象，从缓存的请求体中读取数据
     *
     * @return ServletInputStream 对象
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.bodyByteArray);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // 未实现
            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    /**
     * 重写 getParameterMap() 方法，解决在 Undertow 中流被读取后无法正确获取表单数据的问题
     *
     * @return 请求参数映射表
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameterMap;
    }
}
