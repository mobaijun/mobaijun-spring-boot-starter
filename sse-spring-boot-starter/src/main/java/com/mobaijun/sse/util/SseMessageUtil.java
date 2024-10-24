package com.mobaijun.sse.util;

import com.mobaijun.sse.core.SseEmitterManager;
import com.mobaijun.sse.message.SseMessage;
import org.springframework.stereotype.Component;

/**
 * Description: [sse 工具类]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:53]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Component
public class SseMessageUtil {

    private static SseEmitterManager MANAGER;

    /**
     * 向指定的WebSocket会话发送消息
     *
     * @param userId  要发送消息的用户id
     * @param message 要发送的消息内容
     */
    public static void sendMessage(Long userId, String message) {
        MANAGER.sendMessage(userId, message);
    }

    /**
     * 本机全用户会话发送消息
     *
     * @param message 要发送的消息内容
     */
    public static void sendMessage(String message) {
        MANAGER.sendMessage(message);
    }

    /**
     * 发布SSE订阅消息
     *
     * @param sseMessage 要发布的SSE消息对象
     */
    public static void publishMessage(SseMessage sseMessage) {
        MANAGER.publishMessage(sseMessage);
    }

    /**
     * 向所有的用户发布订阅的消息(群发)
     *
     * @param message 要发布的消息内容
     */
    public static void publishAll(String message) {
        MANAGER.publishAll(message);
    }
}
