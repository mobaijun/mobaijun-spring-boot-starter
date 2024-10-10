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
package com.mobaijun.translation.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mobaijun.translation.handler.TranslationHandler;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: [翻译注解]
 * Author: [mobaijun]
 * Date: [2024/9/28 8:27]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Inherited
@Documented
@JacksonAnnotationsInside
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@JsonSerialize(using = TranslationHandler.class)
public @interface Translation {

    /**
     * 指定翻译类型。
     * <p>
     * 该类型应与实现类上的 {@link TranslationType} 注解中的 type 属性相对应。
     * 默认情况下，使用当前字段的值。如果设置了 {@link #mapper()}，则优先使用映射字段的值。
     * </p>
     */
    String type();

    /**
     * 指定映射字段的名称。
     * <p>
     * 如果该字段不为空，则将使用此字段的值进行翻译。
     * </p>
     */
    String mapper() default "";

    /**
     * 其他条件，例如字典类型（如: sys_user_sex）。
     * <p>
     * 该字段可用于提供额外的上下文信息或筛选条件。
     * </p>
     */
    String other() default "";
}
