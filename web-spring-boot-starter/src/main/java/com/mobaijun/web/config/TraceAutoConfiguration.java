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
package com.mobaijun.web.config;

import com.mobaijun.web.filter.TraceFilter;
import com.mobaijun.web.filter.TraceTaskDecorator;
import com.mobaijun.web.properties.TraceProperties;
import com.mobaijun.web.trace.DefaultTraceIdGenerator;
import com.mobaijun.web.trace.TraceIdGenerator;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Description: [链路追踪自动配置类]
 * Author: [mobaijun]
 * Date: [2025/11/22 01:31]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 * <p>
 * 作为 Spring Boot Starter 的核心：
 * - 注册默认 TraceId 生成器（可被用户覆盖）
 * - 注册链路追踪 Filter
 * - 自动装配 TaskDecorator 以支持异步链路追踪
 * - 支持外部配置覆盖
 */
@Configuration
@EnableConfigurationProperties(TraceProperties.class)
public class TraceAutoConfiguration {

    /**
     * 注册默认 TraceId 生成器
     * 外部可通过定义同名 Bean 覆盖此实现
     */
    @Bean
    @ConditionalOnMissingBean
    public TraceIdGenerator traceIdGenerator() {
        return new DefaultTraceIdGenerator();
    }

    /**
     * 注册链路追踪过滤器，最高优先级
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<TraceFilter> traceFilter(TraceProperties props,
                                                           TraceIdGenerator generator) {
        FilterRegistrationBean<TraceFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TraceFilter(props, generator));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    /**
     * 注册 TaskDecorator 以支持异步任务 TraceId 透传
     * 仅在存在 ThreadPoolTaskExecutor 时有效
     */
    @Bean
    @ConditionalOnBean(ThreadPoolTaskExecutor.class)
    public TaskDecorator traceTaskDecorator(TraceProperties props, TraceIdGenerator generator) {
        return new TraceTaskDecorator(props.getMdcKey(), generator);
    }

    /**
     * 配置 Spring @Async 支持 TraceId 透传
     * 仅在存在 @EnableAsync 注解且用户未自定义 AsyncConfigurer 时生效
     * 如果用户已经配置了 AsyncConfigurer，可以通过配置 ThreadPoolTaskExecutor 的 TaskDecorator 来支持 TraceId
     */
    @Configuration
    @ConditionalOnClass(name = "org.springframework.scheduling.annotation.AsyncConfigurer")
    @ConditionalOnMissingBean(AsyncConfigurer.class)
    static class TraceAsyncConfiguration implements AsyncConfigurer {

        private final TraceProperties traceProperties;
        private final TraceIdGenerator traceIdGenerator;

        TraceAsyncConfiguration(TraceProperties traceProperties, TraceIdGenerator traceIdGenerator) {
            this.traceProperties = traceProperties;
            this.traceIdGenerator = traceIdGenerator;
        }

        @Override
        public Executor getAsyncExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setThreadNamePrefix("async-trace-");
            // 设置 TaskDecorator 以支持 TraceId 透传
            executor.setTaskDecorator(new TraceTaskDecorator(traceProperties.getMdcKey(), traceIdGenerator));
            executor.initialize();
            return executor;
        }

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            // 使用默认的异常处理器
            return null;
        }
    }
}
