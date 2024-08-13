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

import com.mobaijun.loadbalance.core.CustomSpringCloudLoadBalancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Description: [
 *
 * <p>
 * 自定义负载均衡客户端配置类，用于提供自定义的Reactor基负载均衡器。
 * 此类通过Spring的@Configuration注解声明为一个配置类，并使用@ConditionalOnBean注解确保仅当LoadBalancerClientFactory bean存在时才注册自定义负载均衡器。
 *
 * <p>
 * 这个配置类特别适用于Spring Cloud应用，其中你可能需要自定义负载均衡行为，以满足特定的业务需求或性能优化。
 * <p>
 * ]
 * Author: [mobaijun]
 * Date: [2024/8/13 17:32]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@SuppressWarnings("all")
@Configuration(proxyBeanMethods = false)
public class CustomLoadBalanceClientConfiguration {

    /**
     * 创建一个自定义的Reactor基负载均衡器，该负载均衡器使用Spring Cloud的LoadBalancerClientFactory来查找和管理服务实例。
     * 这个方法仅在LoadBalancerClientFactory bean存在于Spring应用上下文中时才会被调用。
     *
     * <p>此方法通过环境变量（通常来源于application.properties或application.yml文件）获取服务名称，
     * 并使用LoadBalancerClientFactory的getLazyProvider方法获取该服务的ServiceInstanceListSupplier实例，
     * 然后利用这些信息创建并返回一个CustomSpringCloudLoadBalancer实例。
     *
     * @param environment               当前应用的环境信息，用于读取配置属性
     * @param loadBalancerClientFactory LoadBalancerClientFactory，用于管理和创建负载均衡器客户端
     * @return 自定义的ReactorLoadBalancer<ServiceInstance>实例，用于服务调用的负载均衡
     */
    @Bean
    @ConditionalOnBean(LoadBalancerClientFactory.class)
    public ReactorLoadBalancer<ServiceInstance> customLoadBalancer(Environment environment,
                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CustomSpringCloudLoadBalancer(name,
                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
