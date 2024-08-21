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
package com.mobaijun.easyexcel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 枚举格式化
 * Author: [mobaijun]
 * Date: [2024/8/20 18:19]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelEnumFormat {

    /**
     * 字典枚举类型
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 字典枚举类中对应的code属性名称，默认为code
     */
    String codeField() default "code";

    /**
     * 字典枚举类中对应的text属性名称，默认为text
     */
    String textField() default "text";
}
