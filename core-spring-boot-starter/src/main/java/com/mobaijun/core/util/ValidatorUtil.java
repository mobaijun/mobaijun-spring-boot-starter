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
package com.mobaijun.core.util;

import com.mobaijun.core.spring.SpringUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

/**
 * Description: [Validator 校验框架工具]
 * Author: [mobaijun]
 * Date: [2024/8/12 10:08]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 注意：此工具类依赖 jakarta.validation.Validator，如果项目中未引入相关依赖，
 * 调用 validate 方法会抛出异常。建议在使用前检查 Validator Bean 是否存在。
 */
public class ValidatorUtil {

    /**
     * 获取 Validator 实例（延迟加载，避免启动时强制要求 Validator Bean）
     */
    private static Validator getValidator() {
        try {
            return SpringUtil.getBean(Validator.class);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Validator bean not found. Please ensure jakarta.validation-api and " +
                            "hibernate-validator dependencies are present.", e);
        }
    }

    /**
     * 对给定对象进行参数校验，并根据指定的校验组进行校验
     *
     * @param object 要进行校验的对象
     * @param groups 校验组
     * @throws jakarta.validation.ConstraintViolationException 如果校验不通过，则抛出参数校验异常
     * @throws IllegalStateException 如果 Validator Bean 不存在，则抛出此异常
     */
    public static <T> void validate(T object, Class<?>... groups) {
        Validator validator = getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException("参数校验异常", validate);
        }
    }
}
