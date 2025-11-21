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

import java.util.UUID;

/**
 * Description: [默认 TraceId 生成器]
 * Author: [mobaijun]
 * Date: [2025/11/22 01:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 使用 UUID 作为 TraceId，去除 "-"，生成 32 位字符串。
 */
public class DefaultTraceIdGenerator implements TraceIdGenerator {

    @Override
    public String generate() {
        // 使用 UUID 并去掉横杠，生成 32 位唯一字符串
        return UUID.randomUUID().toString().replace("-", "");
    }
}
