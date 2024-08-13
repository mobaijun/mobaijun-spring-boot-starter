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
