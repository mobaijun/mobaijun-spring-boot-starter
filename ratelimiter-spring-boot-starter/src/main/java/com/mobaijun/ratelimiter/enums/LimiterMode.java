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
package com.mobaijun.ratelimiter.enums;

/**
 * Description: [限流模式]
 * Author: [mobaijun]
 * Date: [2024/11/14 9:48]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public enum LimiterMode {

    /**
     * 根据IP限流
     */
    IP,

    /**
     * 根据指定的key限流
     */
    KEY,

    /**
     * 二者结合的方式限流
     */
    COMBINATION,
}