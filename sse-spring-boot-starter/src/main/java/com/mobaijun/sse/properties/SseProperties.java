package com.mobaijun.sse.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: [sse 配置文件]
 * Author: [mobaijun]
 * Date: [2024/10/9 17:51]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConfigurationProperties("sse")
public class SseProperties {

    /**
     * sse是否开启
     */
    private Boolean enabled;

    /**
     * 路径
     */
    private String path;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "SseProperties{" +
                "enabled=" + enabled +
                ", path='" + path + '\'' +
                '}';
    }
}
