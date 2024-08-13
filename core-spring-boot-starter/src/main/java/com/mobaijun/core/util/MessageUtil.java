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
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Description: [i18n 工具类]
 * Author: [mobaijun]
 * Date: [2024/7/30 14:41]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
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
