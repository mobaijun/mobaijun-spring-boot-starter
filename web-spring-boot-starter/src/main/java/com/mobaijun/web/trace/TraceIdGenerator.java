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
package com.mobaijun.web.trace;

/**
 * Description: [TraceId ID 生成器接口]
 * Author: [mobaijun]
 * Date: [2025/11/22 01:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 允许开发者通过自定义 Bean 实现不同的 TraceId 生成策略。
 */
public interface TraceIdGenerator {

    /**
     * 生成唯一 TraceId
     *
     * @return TraceId 字符串
     */
    String generate();
}
