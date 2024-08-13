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

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: ApplicationConfig<br>
 * class description: 应用程序基础操作类<br>
 *
 * @author MoBaiJun 2022/12/7 14:13
 */
public class ServerUtil {

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     */
    private static String domainWithoutPort = null;

    /**
     * 获取 url
     *
     * @return url
     */
    public String getUrl() {
        HttpServletRequest request = WebUtil.getRequest();
        return getDomain(request);
    }

    /**
     * 获取去除端口的请求路径，包括：域名，上下文访问路径
     *
     * @return url
     */
    public String getDomainWithoutPort() {
        if (domainWithoutPort == null) {
            HttpServletRequest request = WebUtil.getRequest();
            domainWithoutPort = deletePort(getDomain(request)) +
                    request.getServletContext().getContextPath();
        }

        return domainWithoutPort;
    }


    /**
     * 获取域名
     *
     * @param request HttpServletRequest
     * @return url
     */
    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    /**
     * 截取端口号
     * <a href="https://localhost:8081/common/upload">...</a>
     *
     * @param url 目标 URL
     * @return <a href="https://localhost/profile/upload/2022/03/16/123.jpg">...</a>
     */
    private static String deletePort(String url) {
        return url.substring(0, url.lastIndexOf(":"));
    }
}
