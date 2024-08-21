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
package com.mobaijun.core.config;

import com.mobaijun.core.initializing.ApplicationContextBean;
import com.mobaijun.core.properties.ApplicationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description: [程序注解配置]
 * Author: [mobaijun]
 * Date: [2024/7/30 10:10]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfig {

    /**
     * 初始化配置
     *
     * @param applicationProperties 项目默认配置
     * @return ApplicationContextBean
     */
    @Bean
    @ConditionalOnClass(ApplicationProperties.class)
    public ApplicationContextBean applicationContextBean(
            ApplicationProperties applicationProperties) {
        return new ApplicationContextBean(applicationProperties);
    }
}
