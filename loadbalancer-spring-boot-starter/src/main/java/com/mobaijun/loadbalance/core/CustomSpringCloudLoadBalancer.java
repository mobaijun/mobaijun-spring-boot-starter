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
package com.mobaijun.loadbalance.core;

import cn.hutool.core.net.NetUtil;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

/**
 * Description: [
 * <p>
 * 自定义的Spring Cloud负载均衡器实现，用于选择服务实例。
 * <p>
 * 该实现基于Reactor模式，支持异步和响应式编程。
 * <p>
 * 它通过实现ReactorServiceInstanceLoadBalancer接口来工作，
 * <p>
 * 并根据提供的服务实例列表选择一个实例。
 * <p>
 * ]
 * Author: [mobaijun]
 * Date: [2024/8/13 17:33]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RequiredArgsConstructor
public class CustomSpringCloudLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    /**
     * 要负载均衡的服务ID。
     */
    private final String serviceId;

    /**
     * 提供服务实例列表的提供者。
     * 如果没有可用的提供者，则使用NoopServiceInstanceListSupplier作为默认实现。
     */
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        // 获取服务实例列表提供者，如果没有则使用默认实现
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        // 从提供者获取服务实例列表，并映射到处理实例响应的逻辑
        return supplier.get(request).next().map(serviceInstances -> processInstanceResponse(supplier, serviceInstances));
    }

    /**
     * 处理服务实例响应。
     * 如果服务实例列表提供者实现了SelectedInstanceCallback接口，
     * 则在选择了服务实例后调用其selectedServiceInstance方法。
     *
     * @param supplier         服务实例列表提供者
     * @param serviceInstances 可用的服务实例列表
     * @return 包含所选服务实例的响应
     */
    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
                                                              List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
        // 如果提供者实现了SelectedInstanceCallback接口且响应包含服务器，则调用其回调方法
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    /**
     * 根据提供的服务实例列表获取一个服务实例响应。
     * 如果列表为空，则返回空响应；
     * 如果列表包含本地IP地址的服务实例，则优先返回该实例；
     * 否则，随机选择一个实例返回。
     *
     * @param instances 可用的服务实例列表
     * @return 包含所选服务实例的响应
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: {}", serviceId);
            }
            return new EmptyResponse();
        }
        for (ServiceInstance instance : instances) {
            if (NetUtil.localIpv4s().contains(instance.getHost())) {
                return new DefaultResponse(instance);
            }
        }
        // 如果没有本地实例，则随机选择一个实例
        return new DefaultResponse(instances.get(ThreadLocalRandom.current().nextInt(instances.size())));
    }
}
