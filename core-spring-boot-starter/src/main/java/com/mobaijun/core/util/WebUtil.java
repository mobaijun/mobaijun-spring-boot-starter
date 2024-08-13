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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: ClientToolClass<br>
 * class description: 客户端工具类<br>
 * c
 *
 * @author MoBaiJun 2022/12/7 14:14
 */
public class WebUtil extends WebUtils {

    /**
     * 获取当前 HTTP 请求对象
     *
     * @return 当前 HTTP 请求对象
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取当前 HTTP 响应对象
     *
     * @return 当前 HTTP 响应对象
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取当前 HTTP 会话对象
     *
     * @return 当前 HTTP 会话对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前 Servlet 请求属性对象
     *
     * @return 当前 Servlet 请求属性对象
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
    }
}
