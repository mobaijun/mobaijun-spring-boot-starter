package com.mobaijun.run.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
    private boolean enabled;

    /**
     * 要打开的项目地址列表
     */
    private String projectHomeUrl;

    /**
     * swagger 地址
     */
    private String swaggerUrl;

    /**
     * 前端地址
     */
    private String frontEndAddress;

    public RunProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProjectHomeUrl() {
        return projectHomeUrl;
    }

    public void setProjectHomeUrl(String projectHomeUrl) {
        this.projectHomeUrl = projectHomeUrl;
    }

    public String getSwaggerUrl() {
        return swaggerUrl;
    }

    public void setSwaggerUrl(String swaggerUrl) {
        this.swaggerUrl = swaggerUrl;
    }

    public String getFrontEndAddress() {
        return frontEndAddress;
    }

    public void setFrontEndAddress(String frontEndAddress) {
        this.frontEndAddress = frontEndAddress;
    }

    @Override
    public String toString() {
        return "RunProperties{" +
                "enabled=" + enabled +
                ", projectHomeUrl='" + projectHomeUrl + '\'' +
                ", swaggerUrl='" + swaggerUrl + '\'' +
                ", frontEndAddress='" + frontEndAddress + '\'' +
                '}';
    }
}
