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
package com.mobaijun.api.version.config;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * <p>
 * ApiVersionCondition 类用于根据请求的 URI 中的版本信息（如 /api/v1/）来匹配请求并判断其是否符合特定版本的条件。
 * 该类实现了 Spring MVC 的 RequestCondition 接口，提供了基于版本号的动态请求匹配功能，通常用于支持多版本 API 的版本控制。
 * <p>
 * 主要功能：
 * 1. 从请求 URI 中提取并确定 API 版本（如 "/api/v1/" 或 "/api/v2/"）。
 * 2. 支持版本优先级比较，较高版本的条件会优先匹配。
 * 3. 支持合并多个版本条件，确保灵活的版本匹配。
 * <p>
 * 使用场景：
 * 1. 用于多版本 REST API 的支持，通过 URI 中的版本号来区分不同版本的控制器。
 * 2. 适合需要根据 API 版本动态匹配并返回相应控制器的场景。
 * <p>
 * 例子：
 * - 请求 "/api/v1/..." 会匹配到 v1 版本的控制器。
 * - 请求 "/api/v2/..." 会匹配到 v2 版本的控制器。
 * <p>
 * 本类利用正则表达式解析请求 URI 中的版本信息，并结合 Spring MVC 的 RequestCondition 来动态处理版本路由。
 * <p>
 * Author: [mobaijun]
 * Date: [2024/12/23 11:37]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param apiVersion 存储 API 版本号
 */
public record ApiVersionCondition(int apiVersion) implements RequestCondition<ApiVersionCondition> {

    /**
     * 正则表达式：匹配 URI 中的版本信息，如 /api/v1/、/api/v2/
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile(".*v(\\d+).*");

    /**
     * 构造方法，传入 API 版本号
     *
     * @param apiVersion API 版本号
     */
    public ApiVersionCondition {
    }

    /**
     * 获取 API 版本号
     *
     * @return 返回 API 版本号
     */
    @Override
    public int apiVersion() {
        return apiVersion;
    }

    /**
     * 合并多个版本条件，返回一个新的 ApiVersionCondition。
     *
     * @param other 另一个 ApiVersionCondition 对象
     * @return 返回合并后的 ApiVersionCondition，通常用于处理多个条件
     */
    @NonNull
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.apiVersion());
    }

    /**
     * 比较两个 ApiVersionCondition 的优先级。
     *
     * @param other   另一个 ApiVersionCondition 对象
     * @param request 当前请求对象
     * @return 返回一个整数，表示当前条件与其他条件的优先级关系。较大的版本号优先匹配。
     */
    @Override
    public int compareTo(ApiVersionCondition other, @NonNull HttpServletRequest request) {
        // 返回值越小，优先级越高
        return other.apiVersion() - this.apiVersion;
    }

    /**
     * 根据请求的 URI 判断是否匹配当前版本条件。
     *
     * @param request 当前的 HTTP 请求
     * @return 如果请求 URI 匹配版本条件，则返回当前 ApiVersionCondition，否则返回 null
     */
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        // 获取请求 URI
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());

        // 如果 URI 匹配版本号的正则表达式
        if (m.find()) {
            // 提取版本号
            int version = Integer.parseInt(m.group(1));
            if (version >= this.apiVersion) {
                // 返回当前版本条件
                return this;
            }
        }
        // 如果版本号不匹配，返回 null
        return null;
    }
}
