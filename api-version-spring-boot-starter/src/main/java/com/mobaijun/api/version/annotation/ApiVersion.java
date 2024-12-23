package com.mobaijun.api.version.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: [API版本控制注解]
 * Author: [mobaijun]
 * Date: [2024/12/23 11:36]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

    /**
     * 版本号
     *
     * @return 版本号
     */
    int value() default 1;
}
