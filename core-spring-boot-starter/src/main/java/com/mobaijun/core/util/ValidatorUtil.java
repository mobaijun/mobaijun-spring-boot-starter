package com.mobaijun.core.util;

import com.mobaijun.core.spring.SpringUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Description: [Validator 校验框架工具]
 * Author: [mobaijun]
 * Date: [2024/8/12 10:08]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtil {

    /**
     * 获取 Validator 实例
     */
    private static final Validator VALID = SpringUtil.getBean(Validator.class);

    /**
     * 对给定对象进行参数校验，并根据指定的校验组进行校验
     *
     * @param object 要进行校验的对象
     * @param groups 校验组
     * @throws jakarta.validation.ConstraintViolationException 如果校验不通过，则抛出参数校验异常
     */
    public static <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = VALID.validate(object, groups);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException("参数校验异常", validate);
        }
    }
}
