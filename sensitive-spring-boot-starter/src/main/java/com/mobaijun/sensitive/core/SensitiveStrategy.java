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
package com.mobaijun.sensitive.core;

import cn.hutool.core.util.DesensitizedUtil;
import java.util.function.Function;
import lombok.AllArgsConstructor;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/7/30 9:49]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AllArgsConstructor
public enum SensitiveStrategy {

    /**
     * 身份证脱敏
     */
    ID_CARD(s -> DesensitizedUtil.idCardNum(s, 3, 4)),

    /**
     * 手机号脱敏
     */
    PHONE(DesensitizedUtil::mobilePhone),

    /**
     * 地址脱敏
     */
    ADDRESS(s -> DesensitizedUtil.address(s, 8)),

    /**
     * 邮箱脱敏
     */
    EMAIL(DesensitizedUtil::email),

    /**
     * 银行卡
     */
    BANK_CARD(DesensitizedUtil::bankCard);

    /**
     * 可自行添加其他脱敏策略
     */
    private final Function<String, String> desensitize;

    public Function<String, String> desensitize() {
        return desensitize;
    }
}
