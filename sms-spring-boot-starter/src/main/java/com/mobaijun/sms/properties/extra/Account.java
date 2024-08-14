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
package com.mobaijun.sms.properties.extra;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [扩展属性-用于需要账号密码的平台]
 * Author: [mobaijun]
 * Date: [2024/8/14 16:56]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class Account {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
