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
package com.mobaijun.sse.listener;

import com.mobaijun.sse.core.SseEmitterManager;
import com.mobaijun.sse.message.SseMessage;
import jakarta.annotation.Resource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * Description: [sse 主题订阅监听器]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:53]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class SseTopicListener implements ApplicationRunner, Ordered {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(SseTopicListener.class);

    @Resource
    private SseEmitterManager sseEmitterManager;

    /**
     * 在Spring Boot应用程序启动时初始化SSE主题订阅监听器
     *
     * @param args 应用程序参数
     */
    @Override
    public void run(ApplicationArguments args) {
        sseEmitterManager.subscribeMessage(message -> {
            log.info("SSE主题订阅收到消息session keys={} message={}",
                    message.getUserIds(), message.getMessage());
            sendMessageToUsers(message);
        });

        log.info("初始化SSE主题订阅监听器成功");
    }

    /**
     * 向用户发送消息
     *
     * @param message 消息
     */
    private void sendMessageToUsers(SseMessage message) {
        List<Long> userIds = message.getUserIds();
        String msgContent = message.getMessage();

        if (userIds != null && !userIds.isEmpty()) {
            userIds.forEach(userId -> sseEmitterManager.sendMessage(userId, msgContent));
        } else {
            sseEmitterManager.sendMessage(msgContent);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
