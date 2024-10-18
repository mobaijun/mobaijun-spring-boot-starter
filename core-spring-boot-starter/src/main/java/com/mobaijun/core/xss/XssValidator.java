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
package com.mobaijun.core.xss;

import cn.hutool.core.util.ReUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Description: [自定义xss校验注解实现]
 * Author: [mobaijun]
 * Date: [2024/8/12 10:12]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    /**
     * html标记的正则表达式
     */
    public static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

    /**
     * 重写父类或接口中的 isValid 方法，用于验证给定值是否符合约束条件。
     *
     * @param value                      要验证的值
     * @param constraintValidatorContext 约束验证上下文
     * @return true 如果值有效，否则返回 false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !ReUtil.contains(RE_HTML_MARK, value);
    }
}
