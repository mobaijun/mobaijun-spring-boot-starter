/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.minio.prop;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioConfigurationProperties
 * class description： minio property configuration class
 *
 * @author MoBaiJun 2022/9/19 17:32
 */
@ConfigurationProperties(MinioConfigurationProperties.PREFIX)
public class MinioConfigurationProperties {

    /**
     * 依赖项前缀
     */
    public static final String PREFIX = "spring.minio";

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * Minio 实例的 URL。可以包括 HTTP 协议。必须包括端口。如果未提供端口，则使用 HTTP 的默认端口。
     */
    private String url;

    /**
     * Minio 实例上的访问密钥（登录名）。
     */
    private String accessKey;

    /**
     * Minio 实例上的密钥（密码）。
     */
    private String secretKey;

    /**
     * 如果在 {@code url} 属性中未提供协议，则定义连接是通过 HTTP 还是 HTTPS 进行。
     */
    private boolean secure = false;

    /**
     * 应用程序的桶名称。桶必须已经存在于 Minio 上。
     */
    private String bucket;

    /**
     * 注册到 Actuator 的指标配置前缀。
     */
    private String metricName = "minio.storage";

    /**
     * 定义 Minio 客户端的连接超时时间。
     */
    private Duration connectTimeout = Duration.ofSeconds(10);

    /**
     * 定义 Minio 客户端的写入超时时间。
     */
    private Duration writeTimeout = Duration.ofSeconds(60);

    /**
     * 定义 Minio 客户端的读取超时时间。
     */
    private Duration readTimeout = Duration.ofSeconds(10);

    /**
     * 定义 Minio 客户端的存储过期时间。
     */
    private Duration expire = Duration.ofSeconds(30);

    /**
     * 检查 Minio 实例上是否存在桶。
     * 将此属性设置为 false 将禁用应用程序上下文初始化期间的检查。
     * 此属性仅用于调试目的，因为在运行时操作 Minio 将无法正常工作。
     */
    private boolean checkBucket = true;

    /**
     * 如果桶在 Minio 实例上不存在，则创建桶。
     */
    private boolean createBucket = true;

    public Duration getExpire() {
        return expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public boolean isCheckBucket() {
        return checkBucket;
    }

    public void setCheckBucket(boolean checkBucket) {
        this.checkBucket = checkBucket;
    }

    public boolean isCreateBucket() {
        return createBucket;
    }

    public void setCreateBucket(boolean createBucket) {
        this.createBucket = createBucket;
    }

    @Override
    public String toString() {
        return "MinioConfigurationProperties{" +
                "url='" + url + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", secure=" + secure +
                ", bucket='" + bucket + '\'' +
                ", metricName='" + metricName + '\'' +
                ", connectTimeout=" + connectTimeout +
                ", writeTimeout=" + writeTimeout +
                ", readTimeout=" + readTimeout +
                ", expire=" + expire +
                ", checkBucket=" + checkBucket +
                ", createBucket=" + createBucket +
                '}';
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}