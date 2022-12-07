package com.mobaijun.core.config;

import com.mobaijun.core.util.WebUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: ApplicationConfig<br>
 * class description: 应用程序基础操作类<br>
 *
 * @author MoBaiJun 2022/12/7 14:13
 */
public class ApplicationServerConfig {

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
        url = url.substring(0, url.lastIndexOf(":"));
        return url;
    }
}
