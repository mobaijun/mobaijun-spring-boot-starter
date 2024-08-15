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
package com.mobaijun.i18n.config;

import com.mobaijun.i18n.dynamic.DynamicMessageSource;
import com.mobaijun.i18n.message.MessageSourceHierarchicalChanger;
import com.mobaijun.i18n.provider.I18nMessageProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/8/15 12:40]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration(after = {CustomMessageSourceAutoConfiguration.class, MessageSourceAutoConfiguration.class})
public class I18nMessageSourceAutoConfiguration {

    @ConditionalOnBean(I18nMessageProvider.class)
    @ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public DynamicMessageSource messageSource(I18nMessageProvider i18nMessageProvider) {
        return new DynamicMessageSource(i18nMessageProvider);
    }

    @ConditionalOnBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, value = I18nMessageProvider.class)
    @Bean(name = DynamicMessageSource.DYNAMIC_MESSAGE_SOURCE_BEAN_NAME)
    public DynamicMessageSource dynamicMessageSource(I18nMessageProvider i18nMessageProvider) {
        return new DynamicMessageSource(i18nMessageProvider);
    }

    @ConditionalOnBean(name = {AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME,
            DynamicMessageSource.DYNAMIC_MESSAGE_SOURCE_BEAN_NAME})
    @Bean
    public MessageSourceHierarchicalChanger messageSourceHierarchicalChanger() {
        return new MessageSourceHierarchicalChanger();
    }
}
