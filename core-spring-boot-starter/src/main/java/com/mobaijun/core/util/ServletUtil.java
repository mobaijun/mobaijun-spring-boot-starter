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
package com.mobaijun.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.mobaijun.common.constant.StringConstant;
import com.mobaijun.common.enums.http.HttpStatus;
import com.mobaijun.common.text.StringUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Description: [客户端工具类]
 * Author: [mobaijun]
 * Date: [2024/7/23 15:02]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class ServletUtil extends JakartaServletUtil {

    /**
     * 获取请求中的 String 参数
     *
     * @param name 参数名称
     * @return 请求中对应名称的参数值，如果不存在则返回 null
     * @throws NullPointerException 如果请求对象为空
     */
    public static String getParameter(String name) {
        return Objects.requireNonNull(getRequest()).getParameter(name);
    }

    /**
     * 获取请求中的 String 参数，带默认值
     *
     * @param name         参数名称
     * @param defaultValue 默认值，如果参数不存在则返回该值
     * @return 请求中对应名称的参数值，如果不存在则返回默认值
     * @throws NullPointerException 如果请求对象为空
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(Objects.requireNonNull(getRequest()).getParameter(name), defaultValue);
    }

    /**
     * 获取请求中的 Integer 参数
     *
     * @param name 参数名称
     * @return 请求中对应名称的参数值，转换为 Integer 类型，如果不存在则返回 null
     * @throws NullPointerException 如果请求对象为空
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(Objects.requireNonNull(getRequest()).getParameter(name));
    }

    /**
     * 获取请求中的 Integer 参数，带默认值
     *
     * @param name         参数名称
     * @param defaultValue 默认值，如果参数不存在则返回该值
     * @return 请求中对应名称的参数值，转换为 Integer 类型，如果不存在则返回默认值
     * @throws NullPointerException 如果请求对象为空
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(Objects.requireNonNull(getRequest()).getParameter(name), defaultValue);
    }

    /**
     * 获取请求中的 Boolean 参数
     *
     * @param name 参数名称
     * @return 请求中对应名称的参数值，转换为 Boolean 类型，如果不存在则返回 null
     * @throws NullPointerException 如果请求对象为空
     */
    public static Boolean getParameterToBool(String name) {
        return Convert.toBool(Objects.requireNonNull(getRequest()).getParameter(name));
    }

    /**
     * 获取请求中的 Boolean 参数，带默认值
     *
     * @param name         参数名称
     * @param defaultValue 默认值，如果参数不存在则返回该值
     * @return 请求中对应名称的参数值，转换为 Boolean 类型，如果不存在则返回默认值
     * @throws NullPointerException 如果请求对象为空
     */
    public static Boolean getParameterToBool(String name, Boolean defaultValue) {
        return Convert.toBool(Objects.requireNonNull(getRequest()).getParameter(name), defaultValue);
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link jakarta.servlet.ServletRequest}
     * @return Map
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * 获得所有请求参数
     *
     * @param request 请求对象{@link jakarta.servlet.ServletRequest}
     * @return Map
     */
    public static Map<String, String> getParamMap(ServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtil.join(entry.getValue(), StringConstant.COMMA));
        }
        return params;
    }

    /**
     * 获取当前请求对象 HttpServletRequest
     *
     * @return HttpServletRequest 当前请求对象，如果获取失败则返回 null
     * @throws NullPointerException 如果请求属性对象为 null
     */
    public static HttpServletRequest getRequest() {
        try {
            return Objects.requireNonNull(getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前响应对象 HttpServletResponse
     *
     * @return HttpServletResponse 当前响应对象，如果获取失败则返回 null
     * @throws NullPointerException 如果请求属性对象为 null
     */
    public static HttpServletResponse getResponse() {
        try {
            return Objects.requireNonNull(getRequestAttributes()).getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前请求的 Session 对象
     *
     * @return HttpSession 当前会话对象
     * @throws NullPointerException 如果请求对象为空
     */
    public static HttpSession getSession() {
        return Objects.requireNonNull(getRequest()).getSession();
    }

    /**
     * 获取当前请求的 ServletRequestAttributes 对象
     *
     * @return ServletRequestAttributes 当前请求的属性，如果出现异常则返回 null
     */
    public static ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从请求中获取指定名称的请求头
     *
     * @param request HttpServletRequest 请求对象
     * @param name    请求头名称
     * @return 请求头的值，如果不存在则返回空字符串
     */
    public static String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (value.isEmpty()) {
            return StringConstant.BLANK;
        }
        return urlDecode(value);
    }

    /**
     * 获取请求中的所有请求头，并返回一个不区分大小写的 Map
     *
     * @param request HttpServletRequest 请求对象
     * @return Map 包含所有请求头的 Map（不区分大小写）
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedCaseInsensitiveMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(HttpStatus.OK.getCode());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(string);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request 是&否
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        String accept = request.getHeader("accept");
        if (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StrUtil.equalsAnyIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        return StrUtil.equalsAnyIgnoreCase(ajax, "json", "xml");
    }

    /**
     * 获取客户端的IP地址
     * <p>
     * 该方法通过调用 {@link #getRequest()} 获取当前请求对象，
     * 然后通过重载方法 {@link #getClientIP(HttpServletRequest request, String... otherHeaderNames)} 获取客户端的IP地址。
     * </p>
     *
     * @return 客户端的IP地址
     * @throws NullPointerException 如果当前请求对象为空
     */
    public static String getClientIP() {
        // 获取当前请求对象，并通过重载的 getClientIP 方法获取 IP 地址
        return getClientIP(Objects.requireNonNull(getRequest()));
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }
}
