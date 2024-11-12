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
package com.mobaijun.idempotent.config;

import com.mobaijun.idempotent.aspect.RepeatSubmitAspect;
import com.mobaijun.redisson.config.RedisConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Description: IdempotentAutoConfiguration 自动配置类，负责配置防止重复提交的组件
 * Author: [mobaijun]
 * Date: [2024/11/12 9:08]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration(after = RedisConfiguration.class)
public class IdempotentAutoConfiguration {

    /**
     * 创建 RepeatSubmitAspect Bean，用于防止重复提交的切面。
     * RepeatSubmitAspect 切面会拦截标记了 @RepeatSubmit 注解的方法，并通过 Redis 判断是否重复提交。
     *
     * @return RepeatSubmitAspect 防重复提交的切面
     */
    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }
}