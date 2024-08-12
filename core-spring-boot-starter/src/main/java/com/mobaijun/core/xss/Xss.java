package com.mobaijun.core.xss;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: [自定义 xss 校验注解
 * 用于标记禁止 XSS 攻击的方法或参数。
 * <p>该注解表示被注解的方法或参数不允许包含任何可能导致 XSS 攻击的脚本代码。
 * ]
 * Author: [mobaijun]
 * Date: [2024/8/12 10:12]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {XssValidator.class})
public @interface Xss {

    /**
     * 默认错误消息，表示不允许任何脚本运行。
     *
     * @return 默认错误消息
     */
    String message() default "不允许任何脚本运行";

    /**
     * 分组，用于将多个约束分组。
     *
     * @return 分组数组
     */
    Class<?>[] groups() default {};

    /**
     * 支持的 Payload 类型，用于指定哪些类型的 Payload 被认为是 XSS 攻击。
     *
     * @return 支持的 Payload 类型数组
     */
    Class<? extends Payload>[] payload() default {};
}
