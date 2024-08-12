package com.mobaijun.core.util;

import com.mobaijun.core.spring.SpringUtil;
import java.util.Locale;
import org.springframework.context.MessageSource;

/**
 * Description: [i18n 工具类]
 * Author: [mobaijun]
 * Date: [2024/7/30 14:41]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class MessageUtil {

    /**
     * 通过错误代码获取中文错误信息
     *
     * @param code 错误代码
     * @return 中文错误信息
     */
    public static String getMessage(String code) {
        // 获取 messageSource bean
        MessageSource messageSource = SpringUtil.getBean("messageSource");
        // 获取错误信息，使用默认的中国区域
        return messageSource.getMessage(code, null, Locale.CHINA);
    }

    /**
     * 通过错误代码和参数获取中文错误信息
     *
     * @param code    错误代码
     * @param objects 参数数组
     * @return 中文错误信息
     */
    public static String getMessage(String code, Object... objects) {
        // 获取 messageSource bean
        MessageSource messageSource = SpringUtil.getBean("messageSource");
        // 获取错误信息，使用默认的中国区域
        return messageSource.getMessage(code, objects, Locale.CHINA);
    }

    /**
     * 通过错误代码和参数获取 security 模块的中文错误信息
     *
     * @param code    错误代码
     * @param objects 参数数组
     * @return security 模块的中文错误信息
     */
    public static String getSecurityMessage(String code, Object... objects) {
        // 获取 securityMessageSource bean
        MessageSource messageSource = SpringUtil.getBean("securityMessageSource");
        // 获取错误信息，使用默认的中国区域
        return messageSource.getMessage(code, objects, Locale.CHINA);
    }
}
