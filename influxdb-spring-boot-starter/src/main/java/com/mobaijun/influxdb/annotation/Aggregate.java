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
package com.mobaijun.influxdb.annotation;

import com.mobaijun.influxdb.core.enums.Function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * AnnotationName: Aggregate
 * 注解描述：influxdb 常用聚合函数字段注解
 *
 * @author MoBaiJun 2022/4/29 11:41
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aggregate {

    /**
     * 字段名
     *
     * @return String
     */
    String value();

    /**
     * 字段使用的聚合函数
     *
     * @return Function
     */
    Function tag();
}