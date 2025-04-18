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
package com.mobaijun.ip2region.exception;

import java.io.Serial;

/**
 * Description: [Ip2region异常]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:22]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class Ip2regionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public Ip2regionException(String message) {
        super(message);
    }
}
