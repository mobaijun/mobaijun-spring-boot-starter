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
package com.mobaijun.core.validate.enums;

import com.mobaijun.core.util.reflect.ReflectUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Description: [自定义枚举校验注解实现]
 * Author: [mobaijun]
 * Date: [2024/12/27 11:05]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class EnumPatternValidator implements ConstraintValidator<EnumPattern, String> {

    /**
     * 注解
     */
    private EnumPattern annotation;

    /**
     * 初始化
     *
     * @param annotation 注解
     */
    @Override
    public void initialize(EnumPattern annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.annotation = annotation;
    }

    /**
     * 校验
     *
     * @param value                      待校验值
     * @param constraintValidatorContext 校验上下文
     * @return 是否校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotBlank(value)) {
            String fieldName = annotation.fieldName();
            for (Object e : annotation.type().getEnumConstants()) {
                if (value.equals(ReflectUtil.invokeGetter(e, fieldName))) {
                    return true;
                }
            }
        }
        return false;
    }
}