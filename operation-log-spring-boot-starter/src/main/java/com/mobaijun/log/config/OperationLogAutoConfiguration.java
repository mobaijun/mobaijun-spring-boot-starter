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
package com.mobaijun.log.config;

import com.mobaijun.log.aspect.OperationLogAspect;
import com.mobaijun.log.handler.OperationLogHandler;
import com.mobaijun.log.properties.OperationLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Description: []
 * Author: [mobaijun]
 * Date: [2024/8/15 11:07]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@EnableConfigurationProperties(OperationLogProperties.class)
@ConditionalOnProperty(prefix = OperationLogProperties.PREFIX, name = "enabled", matchIfMissing = true,
        havingValue = "true")
public class OperationLogAutoConfiguration {

    /**
     * 注册操作日志Aspect
     *
     * @return OperationLogAspect
     */
    @Bean
    @ConditionalOnBean(OperationLogHandler.class)
    public <T> OperationLogAspect<T> operationLogAspect(OperationLogHandler<T> operationLogHandler) {
        return new OperationLogAspect<>(operationLogHandler);
    }
}
