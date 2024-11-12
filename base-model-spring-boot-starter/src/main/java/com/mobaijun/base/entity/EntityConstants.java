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
package com.mobaijun.base.entity;

/**
 * Description: [实体常量]
 * Author: [mobaijun]
 * Date: [2024/9/27 8:50]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public final class EntityConstants {

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认的日期格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认的日期正则表达式
     */
    public static final String DEFAULT_DATE_REGEXP = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    /**
     * 默认的日期正则表达式
     */
    public static final String DEFAULT_DATE_TIME_REGEXP = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s(0\\d|1\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
}
