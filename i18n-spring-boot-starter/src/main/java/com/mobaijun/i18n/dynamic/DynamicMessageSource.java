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
package com.mobaijun.i18n.dynamic;

import com.mobaijun.i18n.model.I18nMessage;
import com.mobaijun.i18n.provider.I18nMessageProvider;
import io.micrometer.common.lang.NonNullApi;
import io.micrometer.common.lang.Nullable;
import java.text.MessageFormat;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;

/**
 * Description: 动态获取的 MessageSource，比如从数据库 或者 redis 中获取 message 信息
 * Author: [mobaijun]
 * Date: [2024/8/15 11:57]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@NonNullApi
@RequiredArgsConstructor
public class DynamicMessageSource extends AbstractMessageSource {

    /**
     * 动态 message source bean name
     */
    public static final String DYNAMIC_MESSAGE_SOURCE_BEAN_NAME = "dynamicMessageSource";

    /**
     * 国际化消息提供者
     */
    private final I18nMessageProvider i18nMessageProvider;

    @Override
    @Nullable
    protected MessageFormat resolveCode(String code, Locale locale) {
        I18nMessage i18nMessage = this.i18nMessageProvider.getI18nMessage(code, locale);
        if (i18nMessage != null) {
            return createMessageFormat(i18nMessage.getMessage(), locale);
        }
        return null;
    }
}

