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
package com.mobaijun.websocket.distribute;

import com.mobaijun.json.utils.JsonUtil;
import com.mobaijun.websocket.dto.MessageDTO;
import com.mobaijun.websocket.exception.ErrorJsonMessageException;
import com.mobaijun.websocket.session.WebSocketSessionStore;
import jakarta.websocket.SendResult;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description: 消息分发器
 * Author: [mobaijun]
 * Date: [2024/8/23 9:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@RocketMQMessageListener(
        consumerGroup = "${spring.application.name:default-application}-${spring.profiles.active:dev}",
        topic = "${spring.application.name:default-application}-${spring.profiles.active:dev}",
        selectorExpression = "${websocket.mq.tag}", messageModel = MessageModel.BROADCASTING)
public class RocketmqMessageDistributor extends AbstractMessageDistributor implements RocketMQListener<MessageExt> {

    // RocketMQ 的模板，用于发送和接收消息
    private final RocketMQTemplate template;
    /**
     * 从配置文件中注入应用名称
     */
    @Value("${spring.application.name}")
    private String appName;
    /**
     * 从配置文件中注入消息标签
     */
    @Value("${ballcat.websocket.mq.tag}")
    private String tag;

    /**
     * 构造函数
     *
     * @param webSocketSessionStore WebSocket 会话存储，用于管理会话
     * @param template              RocketMQ 的模板，用于发送和接收消息
     */
    public RocketmqMessageDistributor(WebSocketSessionStore webSocketSessionStore, RocketMQTemplate template) {
        super(webSocketSessionStore);
        this.template = template;
    }

    /**
     * 消息分发
     * <p>
     * 将消息对象转换为 JSON 字符串，并通过 RocketMQ 发送到指定的目标。目标由应用名称和标签组成。
     *
     * @param message 发送的消息对象
     */
    @Override
    public void distribute(MessageDTO message) {
        log.info("the send message body is [{}]", message);

        // 目标字符串，由应用名称和标签组成
        String destination = this.appName + ":" + this.tag;

        // 发送消息到指定目标，并接收发送结果
        SendResult sendResult = this.template.sendAndReceive(destination, JsonUtil.toJsonString(message), SendResult.class);

        if (log.isDebugEnabled()) {
            log.debug("send message to `{}` finished. result:{}", destination, sendResult);
        }
    }

    /**
     * 消息消费
     * <p>
     * 接收到 RocketMQ 消息后，解析消息内容并处理。处理过程中如有异常会记录日志并抛出自定义异常。
     *
     * @param message 接收到的消息对象
     */
    @Override
    public void onMessage(MessageExt message) {
        // 将消息体转换为字符串
        String body = new String(message.getBody(), StandardCharsets.UTF_8);

        // 解析消息内容为 MessageDTO 对象
        MessageDTO event = JsonUtil.parseObject(body, MessageDTO.class);
        log.info("the content is [{}]", event);

        try {
            assert event != null; // 确保消息对象不为空
            // 执行实际的消息处理逻辑
            this.doSend(event);
        } catch (Exception e) {
            // 处理异常并记录日志
            log.error("MQ消费信息处理异常: {}", e.getMessage(), e);
            // 抛出自定义异常
            throw new ErrorJsonMessageException("MQ消费信息处理异常, " + e.getMessage());
        }
    }
}
