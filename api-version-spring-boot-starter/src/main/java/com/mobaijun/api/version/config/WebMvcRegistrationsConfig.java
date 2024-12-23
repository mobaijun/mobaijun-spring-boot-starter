package com.mobaijun.api.version.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Description:
 * <p>
 * 它实现了 WebMvcRegistrations 接口并覆盖了 getRequestMappingHandlerMapping() 方法，
 * 用来自定义 Spring MVC 的请求映射处理器（RequestMappingHandlerMapping）。
 * <p>
 * Author: [mobaijun]
 * Date: [2024/12/23 11:40]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
public class WebMvcRegistrationsConfig implements WebMvcRegistrations {

    /**
     * 用于自定义 Spring MVC 的请求映射处理器。
     * <p>
     * 该方法返回一个自定义的 `ApiRequestMappingHandlerMapping` 实例，
     * 使得请求的路由逻辑能够根据自定义的规则（例如 API 版本）进行匹配。
     * <p>
     * 通过替换默认的 `RequestMappingHandlerMapping`，我们可以根据请求 URL 中的版本号（如 /api/v1、/api/v2 等）
     * 来选择适当的控制器和方法进行处理。
     *
     * @return 自定义的 `RequestMappingHandlerMapping`，这里是 `ApiRequestMappingHandlerMapping`。
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        // 返回自定义的请求映射处理器
        return new ApiRequestMappingHandlerMapping();
    }
}

