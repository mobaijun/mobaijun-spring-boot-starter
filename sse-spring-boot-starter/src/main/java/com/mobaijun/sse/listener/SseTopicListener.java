package com.mobaijun.sse.listener;

import com.mobaijun.sse.core.SseEmitterManager;
import jakarta.annotation.Resource;
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
     * @throws Exception 初始化过程中可能抛出的异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        sseEmitterManager.subscribeMessage((message) -> {
            log.info("SSE主题订阅收到消息session keys={} message={}", message.getUserIds(), message.getMessage());
            // 如果key不为空就按照key发消息 如果为空就群发
            if (!message.getUserIds().isEmpty()) {
                message.getUserIds().forEach(key -> sseEmitterManager.sendMessage(key, message.getMessage()));
            } else {
                sseEmitterManager.sendMessage(message.getMessage());
            }
        });
        log.info("初始化SSE主题订阅监听器成功");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
