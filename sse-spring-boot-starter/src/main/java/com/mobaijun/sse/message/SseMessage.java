package com.mobaijun.sse.message;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Description: [sse 消息实体]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class SseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要推送到的session key 列表
     */
    private List<Long> userIds;

    /**
     * 需要发送的消息
     */
    private String message;

    public SseMessage() {
    }

    public SseMessage(List<Long> userIds, String message) {
        this.userIds = userIds;
        this.message = message;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SseMessage{" +
                "userIds=" + userIds +
                ", message='" + message + '\'' +
                '}';
    }
}
