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
package com.mobaijun.dubbo.config;

import java.net.Inet6Address;
import java.net.InetAddress;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.Ordered;

/**
 * Description: [dubbo自定义IP注入(避免IP不正确问题)]
 * Author: [mobaijun]
 * Date: [2024/8/5 11:57]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    /**
     * 获取该 BeanFactoryPostProcessor 的顺序，确保它在容器初始化过程中具有最高优先级
     *
     * @return 优先级顺序值，越小优先级越高
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 在 Spring 容器初始化过程中对 Bean 工厂进行后置处理
     *
     * @param beanFactory 可配置的 Bean 工厂
     * @throws org.springframework.beans.BeansException 如果在处理过程中发生错误
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取 InetUtils bean，用于获取 IP 地址
        InetUtils inetUtils = beanFactory.getBean(InetUtils.class);
        String ip = "127.0.0.1";
        // 获取第一个非回环地址
        InetAddress address = inetUtils.findFirstNonLoopbackAddress();
        if (address != null) {
            if (address instanceof Inet6Address) {
                // 处理 IPv6 地址
                String ipv6AddressString = address.getHostAddress();
                if (ipv6AddressString.contains("%")) {
                    // 去掉可能存在的范围 ID
                    ipv6AddressString = ipv6AddressString.substring(0, ipv6AddressString.indexOf("%"));
                }
                ip = ipv6AddressString;
            } else {
                // 处理 IPv4 地址
                ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            }
        }
        // 设置系统属性 DUBBO_IP_TO_REGISTRY 为获取到的 IP 地址
        System.setProperty("DUBBO_IP_TO_REGISTRY", ip);
    }
}
