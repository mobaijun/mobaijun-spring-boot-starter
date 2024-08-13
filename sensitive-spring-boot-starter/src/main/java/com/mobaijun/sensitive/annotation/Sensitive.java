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
package com.mobaijun.sensitive.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mobaijun.sensitive.core.SensitiveStrategy;
import com.mobaijun.sensitive.handler.SensitiveHandler;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: [用于标记敏感字段的注解。
 * 此注解用于指定字段的脱敏策略、角色键和权限。]
 * Author: [mobaijun]
 * Date: [2024/7/30 9:46]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveHandler.class)
public @interface Sensitive {

    /**
     * 指定用于脱敏的策略。
     *
     * @return 脱敏策略。
     */
    SensitiveStrategy strategy();

    /**
     * 角色键，用于指定哪个角色可以查看或处理敏感数据。
     * 默认为空字符串。
     *
     * @return 角色键。
     */
    String roleKey() default "";

    /**
     * 权限，用于指定需要什么权限来查看或处理敏感数据。
     * 默认为空字符串。
     *
     * @return 权限。
     */
    String perms() default "";
}
