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
import com.mobaijun.websocket.distribute.LocalMessageDistributor;
import com.mobaijun.websocket.distribute.MessageDistributor;
import com.mobaijun.websocket.properties.WebSocketProperties;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 本地的消息分发器配置
 * Author: [mobaijun]
 * Date: [2024/8/23 9:51]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "message-distributor",
        havingValue = MessageDistributorTypeConstants.LOCAL, matchIfMissing = true)
public class LocalMessageDistributorConfig {

    /**
     * 本地基于内存的消息分发，不支持集群
     */
    private final WebSocketSessionStore webSocketSessionStore;

    /**
     * 配置本地基于内存的消息分发器。
     *
     * <p>此消息分发器依赖于 {@link WebSocketSessionStore} 进行 WebSocket 会话的管理，
     * 只适用于单机环境，不支持集群下的消息分发。
     *
     * @return 配置好的本地消息分发器实例 {@link LocalMessageDistributor}
     */
    @Bean
    @ConditionalOnMissingBean(MessageDistributor.class)
    public LocalMessageDistributor messageDistributor() {
        return new LocalMessageDistributor(this.webSocketSessionStore);
    }
}
