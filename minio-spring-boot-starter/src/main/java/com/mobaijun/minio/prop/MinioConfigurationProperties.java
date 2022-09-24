package com.mobaijun.minio.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioConfigurationProperties
 * class description： minio property configuration class
 *
 * @author MoBaiJun 2022/9/19 17:32
 */
@ConfigurationProperties(MinioConfigurationProperties.PREFIX)
@EnableConfigurationProperties(MinioConfigurationProperties.class)
public class MinioConfigurationProperties {

    /**
     * dependency prefix
     */
    public static final String PREFIX = "spring.minio";

    /**
     * URL for Minio instance. Can include the HTTP scheme. Must include the port. If the port is not provided, then the port of the HTTP is taken.
     */
    private String url;

    /**
     * Access key (login) on Minio instance
     */
    private String accessKey;

    /**
     * Secret key (password) on Minio instance
     */
    private String secretKey;

    /**
     * If the scheme is not provided in {@code url} property, define if the connection is done via HTTP or HTTPS.
     */
    private boolean secure = false;

    /**
     * Bucket name for the application. The bucket must already exists on Minio.
     */
    private String bucket;

    /**
     * Metric configuration prefix which are registered on Actuator.
     */
    private String metricName = "minio.storage";

    /**
     * Define to connect timeout for the Minio Client.
     */
    private Duration connectTimeout = Duration.ofSeconds(10);

    /**
     * Define to write timeout for the Minio Client.
     */
    private Duration writeTimeout = Duration.ofSeconds(60);

    /**
     * Define the read timeout for the Minio Client.
     */
    private Duration readTimeout = Duration.ofSeconds(10);

    /**
     * Defines the storage expiration time for the Minio client
     */
    private Duration expire = Duration.ofSeconds(30);

    /**
     * Check if the bucket exists on Minio instance.
     * Settings this false will disable the check during the application context initialization.
     * This property should be used for debug purpose only, because operations on Minio will not work during runtime.
     */
    private boolean checkBucket = true;

    /**
     * Will create the bucket if it does not exist on the Minio instance.
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
}
