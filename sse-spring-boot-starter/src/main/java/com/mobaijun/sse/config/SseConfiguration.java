package com.mobaijun.sse.config;

import com.mobaijun.sse.controller.SseController;
import com.mobaijun.sse.core.SseEmitterManager;
import com.mobaijun.sse.listener.SseTopicListener;
import com.mobaijun.sse.properties.SseProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: [sse 配置类]
 * Author: [mobaijun]
 * Date: [2024/10/24 13:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
@ConditionalOnProperty(value = "sse.enabled", havingValue = "true")
@EnableConfigurationProperties(SseProperties.class)
public class SseConfiguration {

    @Bean
    public SseEmitterManager sseEmitterManager() {
        return new SseEmitterManager();
    }

    @Bean
    public SseTopicListener sseTopicListener() {
        return new SseTopicListener();
    }

    @Bean
    public SseController sseController(SseEmitterManager sseEmitterManager) {
        return new SseController(sseEmitterManager);
    }
}
