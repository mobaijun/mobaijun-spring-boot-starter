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

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

/**
 * Description: [自定义负载均衡自动配置]
 * Author: [mobaijun]
 * Date: [2024/8/13 17:32]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@LoadBalancerClients(defaultConfiguration = CustomLoadBalanceClientConfiguration.class)
public class CustomLoadBalanceAutoConfiguration {
}
