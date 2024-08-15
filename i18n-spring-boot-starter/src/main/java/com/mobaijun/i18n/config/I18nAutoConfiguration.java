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

import com.mobaijun.i18n.advice.I18nResponseAdvice;
import com.mobaijun.i18n.properties.I18nProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/8/15 12:40]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration(after = I18nMessageSourceAutoConfiguration.class)
@EnableConfigurationProperties(I18nProperties.class)
public class I18nAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public I18nResponseAdvice i18nResponseAdvice(MessageSource messageSource, I18nProperties i18nProperties) {
        return new I18nResponseAdvice(messageSource, i18nProperties);
    }
}
