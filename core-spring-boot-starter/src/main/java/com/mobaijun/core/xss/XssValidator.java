package com.mobaijun.core.xss;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HtmlUtil;
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
     * 重写父类或接口中的 isValid 方法，用于验证给定值是否符合约束条件。
     *
     * @param value                      要验证的值
     * @param constraintValidatorContext 约束验证上下文
     * @return true 如果值有效，否则返回 false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !ReUtil.contains(HtmlUtil.RE_HTML_MARK, value);
    }
}
