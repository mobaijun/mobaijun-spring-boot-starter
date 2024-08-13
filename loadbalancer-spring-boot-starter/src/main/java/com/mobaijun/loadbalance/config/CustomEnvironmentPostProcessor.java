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
package com.mobaijun.loadbalance.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Description: [
 * <p>
 * 自定义的环境后处理器，用于在Spring Boot应用启动过程中修改环境配置。
 * 此类实现了Spring Boot的{@link EnvironmentPostProcessor}接口，允许在环境属性被读取并用于配置绑定之前修改它们。
 *
 * <p>
 * 这个处理器通过修改系统属性来影响Dubbo消费者的负载均衡策略，
 * 将Dubbo消费者的负载均衡策略设置为自定义的负载均衡器"customDubboLoadBalancer"。
 * 这对于需要在应用启动时动态调整Dubbo配置的场景非常有用。
 *
 * <p>
 * 通过实现{@link Ordered}接口，并返回{@link Ordered#HIGHEST_PRECEDENCE}，
 * 确保这个处理器在所有其他环境后处理器之前运行，以确保自定义的负载均衡策略能够尽早生效。
 * <p>
 * ]
 * Author: [mobaijun]
 * Date: [2024/8/13 17:32]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    /**
     * 在Spring Boot应用的环境属性被最终确定之前，修改环境配置。
     * 这里，将系统属性"dubbo.consumer.loadbalance"设置为"customDubboLoadBalancer"，
     * 以影响Dubbo消费者的负载均衡行为。
     *
     * @param environment 可配置的环境对象，用于读取和修改环境属性
     * @param application 当前正在启动的SpringApplication实例
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.setProperty("dubbo.consumer.loadbalance", "customDubboLoadBalancer");
    }

    /**
     * 返回处理器的执行顺序。
     * 通过返回{@link Ordered#HIGHEST_PRECEDENCE}，确保此处理器在所有其他环境后处理器之前运行。
     *
     * @return 执行顺序，值越小，优先级越高
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
