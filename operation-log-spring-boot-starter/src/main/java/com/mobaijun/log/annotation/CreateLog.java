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
package com.mobaijun.log.annotation;

import com.mobaijun.log.enums.OperationTypes;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * Description: [创建日志]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:59]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OperationLog(type = OperationTypes.CREATE)
public @interface CreateLog {

    /**
     * 日志信息
     *
     * @return 日志描述信息
     */
    @AliasFor(annotation = OperationLog.class)
    String msg();

    /**
     * 是否保存方法入参
     *
     * @return boolean
     */
    @AliasFor(annotation = OperationLog.class)
    boolean recordParams() default true;

    /**
     * 是否保存方法返回值
     *
     * @return boolean
     */
    @AliasFor(annotation = OperationLog.class)
    boolean recordResult() default true;
}
