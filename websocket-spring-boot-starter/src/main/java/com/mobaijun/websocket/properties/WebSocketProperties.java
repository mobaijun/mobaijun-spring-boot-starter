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
package com.mobaijun.websocket.properties;

import com.mobaijun.websocket.handler.ConcurrentWebSocketSessionOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Description: WebSocket 配置类
 * Author: [mobaijun]
 * Date: [2024/8/23 9:08]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = WebSocketProperties.PREFIX)
public class WebSocketProperties {

    /**
     * 配置前缀
     */
    public static final String PREFIX = "websocket";

    /**
     * 开启 websocket, 默认开启
     */
    private boolean enabled = true;

    /**
     * 路径: 无参: /ws 有参: PathVariable: 单参: /ws/{test} 多参: /ws/{test1}/{test2} query:
     * /ws?uid=1&name=test
     */
    private String path = "/ws";

    /**
     * 允许跨域的源
     */
    private String[] allowedOrigins;

    /**
     * 允许跨域来源的匹配规则
     */
    private String[] allowedOriginPatterns = new String[]{"*"};

    /**
     * 是否支持部分消息
     */
    private boolean supportPartialMessages = false;

    /**
     * 心跳处理
     */
    private boolean heartbeat = true;

    /**
     * 是否开启对session的映射记录
     */
    private boolean mapSession = true;

    /**
     * 是否开启 sockJs 支持
     */
    private boolean withSockjs = false;

    /**
     * 多线程发送相关配置
     */
    @NestedConfigurationProperty
    private ConcurrentWebSocketSessionOptions concurrent = new ConcurrentWebSocketSessionOptions();

    /**
     * 消息分发器：local | redis，默认 local, 如果自定义的话，可以配置为其他任意值
     */
    private WebSocketProperties.MessageDistributorTypeEnum messageDistributor = WebSocketProperties.MessageDistributorTypeEnum.LOCAL;

    /**
     * 消息分发器类型，用于解决集群场景下的消息跨节点推送问题
     */
    enum MessageDistributorTypeEnum {

        /**
         * 本地缓存，不做跨节点分发
         */
        LOCAL,

        /**
         * redis，利用 redis pub/sub 处理
         */
        REDIS,

        /**
         * 基于 rocketmq 广播
         */
        ROCKETMQ,

        /**
         * 自定义，用户自己实现一个 MessageDistributor
         */
        CUSTOM
    }
}
