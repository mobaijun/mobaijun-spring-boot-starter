package com.mobaijun.run.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: [run 配置文件]
 * Author: [mobaijun]
 * Date: [2023/11/23 11:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConfigurationProperties(prefix = RunProperties.PREFIX)
public class RunProperties {

    /**
     * 前缀
     */
    public final static String PREFIX = "spring.run.web-server";

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 地址集合
     */
    private List<String> url = new ArrayList<>();

    public RunProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "RunProperties{" +
                "enabled=" + enabled +
                ", url=" + url +
                '}';
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
