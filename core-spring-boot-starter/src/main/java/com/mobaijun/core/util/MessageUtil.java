package com.mobaijun.core.util;

import com.mobaijun.core.spring.SpringUtil;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Description: [i18n 工具类]
 * Author: [mobaijun]
 * Date: [2024/7/30 14:41]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

    private static final MessageSource MESSAGE_SOURCE = SpringUtil.getBean(MessageSource.class);

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

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        try {
            return MESSAGE_SOURCE.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return code;
        }
    }
}
