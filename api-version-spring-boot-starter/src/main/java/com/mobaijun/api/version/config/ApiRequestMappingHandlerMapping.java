package com.mobaijun.api.version.config;

import com.mobaijun.api.version.annotation.ApiVersion;
import io.micrometer.common.lang.NonNull;
import java.lang.reflect.Method;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Description: [扩展 Spring MVC 的 RequestMappingHandlerMapping，用于自定义 API 请求的版本控制]
 * Author: [mobaijun]
 * Date: [2024/12/23 11:39]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 版本标识符
     */
    private static final String VERSION_FLAG = "{version}";

    /**
     * 根据类上的 @RequestMapping 注解和 @ApiVersion 注解，创建对应的请求条件
     *
     * @param clazz 目标类
     * @return 返回 ApiVersionCondition 或 null
     */
    private static RequestCondition<ApiVersionCondition> createCondition(Class<?> clazz) {
        // 获取类上的 @RequestMapping 注解
        RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);

        // 如果类上没有 @RequestMapping 注解，直接返回 null
        if (classRequestMapping == null) {
            return null;
        }

        // 获取类的映射路径（通常是 @RequestMapping 的 value 属性）
        String[] mappingUrls = classRequestMapping.value();
        if (mappingUrls.length == 0) {
            // 如果没有定义映射路径，直接返回 null
            return null;
        }

        // 获取第一个映射路径
        String mappingUrl = mappingUrls[0];

        // 如果路径中不包含版本标识符，则不需要处理版本条件
        if (!mappingUrl.contains(VERSION_FLAG)) {
            return null;
        }

        // 如果类上有 @ApiVersion 注解，则根据其值创建 ApiVersionCondition
        ApiVersion apiVersion = clazz.getAnnotation(ApiVersion.class);
        // 默认版本是 1，如果没有定义 @ApiVersion 注解，则使用默认值
        int version = apiVersion == null ? 1 : apiVersion.value();

        // 返回 ApiVersionCondition
        return new ApiVersionCondition(version);
    }

    /**
     * 获取方法级别的自定义条件
     *
     * @param method 目标方法
     * @return 返回方法的请求条件
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        // 根据方法的声明类获取自定义条件
        return createCondition(method.getDeclaringClass());
    }

    /**
     * 获取类型级别的自定义条件
     *
     * @param handlerType 目标处理器类型（类）
     * @return 返回类型的请求条件
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(@NonNull Class<?> handlerType) {
        // 根据类获取自定义条件
        return createCondition(handlerType);
    }
}
