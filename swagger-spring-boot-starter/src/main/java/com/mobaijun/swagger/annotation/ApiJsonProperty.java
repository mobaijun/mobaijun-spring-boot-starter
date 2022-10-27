/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MoBaiJun 2022/4/26 9:25
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonProperty {

    /**
     * 参数描述
     *
     * @return String
     */
    String value() default "";

    /**
     * 字段名称
     *
     * @return String
     */
    String name();

    /**
     * 数据类型 （支持string、integer、long、double、date）
     *
     * @return String
     */
    String dataType() default "string";

    /**
     * 参数是否必填
     *
     * @return boolean
     */
    boolean required() default false;

    /**
     * 示例
     *
     * @return String
     */
    String example() default "";
}