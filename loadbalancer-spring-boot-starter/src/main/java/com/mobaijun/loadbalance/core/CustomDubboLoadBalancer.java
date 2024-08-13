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
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

/**
 * Description: [自定义 Dubbo 负载均衡算法]
 * Author: [mobaijun]
 * Date: [2024/8/13 17:33]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class CustomDubboLoadBalancer extends AbstractLoadBalance {

    /**
     * 自定义的负载均衡选择方法，用于在多个Invoker中选择一个进行服务调用。
     * 此方法首先尝试选择与当前服务器处于同一局域网（或具有相同IP地址段）的Invoker，
     * 如果没有找到这样的Invoker，则随机选择一个Invoker进行服务调用。
     *
     * @param invokers   服务提供者的Invoker列表，每个Invoker代表一个可用的服务提供者实例。
     * @param url        调用者URL，包含调用的各种参数和配置信息。
     * @param invocation 调用信息，包含调用的方法名、参数等信息。
     * @return 返回选中的Invoker，用于后续的服务调用。
     */
    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 遍历所有的Invoker
        for (Invoker<T> invoker : invokers) {
            // 获取Invoker的URL，并从中提取主机名
            String host = invoker.getUrl().getHost();
            // 检查当前服务器的本地IPv4地址列表中是否包含该Invoker的主机名
            // 注意：这里假设NetUtil.localIpv4s()返回的是IP地址列表，但直接比较IP和主机名可能不准确，
            // 实际中可能需要解析主机名为IP后进行比较，或者采用其他机制来识别局域网内的Invoker。
            if (NetUtil.localIpv4s().stream().anyMatch(ip -> ip.endsWith(host) || host.endsWith(ip))) { // 假设的简化比较逻辑
                // 如果找到与当前服务器在同一局域网（或IP相同）的Invoker，则返回该Invoker
                return invoker;
            }
        }
        // 如果没有找到与当前服务器在同一局域网（或IP相同）的Invoker，则随机选择一个Invoker
        return invokers.get(ThreadLocalRandom.current().nextInt(invokers.size()));
    }
}
