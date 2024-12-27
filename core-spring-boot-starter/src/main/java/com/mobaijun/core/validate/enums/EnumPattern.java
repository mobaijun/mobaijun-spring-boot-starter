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
package com.mobaijun.core.validate.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Description: [自定义枚举校验]
 * Author: [mobaijun]
 * Date: [2024/12/27 11:04]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Documented
@Retention(RUNTIME)
// 允许在同一元素上多次使用该注解
@Repeatable(EnumPattern.List.class)
@Constraint(validatedBy = {EnumPatternValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface EnumPattern {

    /**
     * 需要校验的枚举类型
     */
    Class<? extends Enum<?>> type();

    /**
     * 枚举类型校验值字段名称
     * 需确保该字段实现了 getter 方法
     */
    String fieldName();

    /**
     * 校验失败时的提示信息
     *
     * @return 提示信息
     */
    String message() default "输入值不在枚举范围内";

    /**
     * 校验分组
     *
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     *
     * @return 负载
     */
    Class<? extends Payload>[] payload() default {};

    @Documented
    @Retention(RUNTIME)
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @interface List {

        /**
         * 自定义枚举校验注解组
         *
         * @return 枚举校验注解组
         */
        EnumPattern[] value();
    }
}