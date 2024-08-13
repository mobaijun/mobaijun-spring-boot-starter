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
package com.mobaijun.dubbo.enums;

import lombok.AllArgsConstructor;

/**
 * Description: [请求日志泛型]
 * Author: [mobaijun]
 * Date: [2024/8/5 11:56]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AllArgsConstructor
public enum RequestLogEnum {

    /**
     * info 基础信息
     */
    INFO,

    /**
     * param 参数信息
     */
    PARAM,

    /**
     * full 全部
     */
    FULL;
}
