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
package com.mobaijun.websocket.configuration;

import com.mobaijun.websocket.constant.MessageDistributorTypeConstants;
import com.mobaijun.websocket.distribute.MessageDistributor;
import com.mobaijun.websocket.distribute.RocketmqMessageDistributor;
import com.mobaijun.websocket.properties.WebSocketProperties;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: RocketMQ 消息分发器配置类
 * <p>当配置属性中指定使用 RocketMQ 作为消息分发器时，启用此配置类，配置基于 RocketMQ 的消息分发器。
 * Author: [mobaijun]
 * Date: [2024/8/23 9:25]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "message-distributor",
        havingValue = MessageDistributorTypeConstants.ROCKETMQ)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class RocketMqMessageDistributorConfig {

    /**
     * 配置 RocketMQ 消息分发器。
     */
    private final WebSocketSessionStore webSocketSessionStore;

    /**
     * 配置 RocketMQ 消息分发器。
     *
     * <p>如果没有其他自定义的 {@link MessageDistributor} 实例，则会创建并返回
     * 基于 RocketMQ 的 {@link RocketmqMessageDistributor}，用于分布式环境中的消息分发。
     *
     * @param template RocketMQ 操作模板，依赖注入
     * @return 配置好的 RocketMQ 消息分发器实例
     */
    @Bean
    @ConditionalOnMissingBean(MessageDistributor.class)
    public RocketmqMessageDistributor messageDistributor(RocketMQTemplate template) {
        return new RocketmqMessageDistributor(this.webSocketSessionStore, template);
    }
}
