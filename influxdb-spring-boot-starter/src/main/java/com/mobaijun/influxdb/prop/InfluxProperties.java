package com.mobaijun.influxdb.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InfluxProperties
 * 类描述： 实体类，用来映射配置信息
 *
 * @author MoBaiJun 2022/4/27 8:53
 */
@ConfigurationProperties(InfluxProperties.PREFIX)
public class InfluxProperties {

    /**
     * 映射配置文件路径
     */
    public static final String PREFIX = "spring.influxdb";

    /**
     * InfluxDB http url default "http://localhost:8088"
     */
    private String url = "http://localhost:8088";

    /**
     * auth username
     */
    private String username;

    /**
     * auth password
     */
    private String password;

    /**
     * database for http
     */
    private String database;

    /**
     * storage strategy default ”autogen“
     */
    private String retentionPolicy = "autogen";

    /**
     * data expiration time
     */
    private String retentionPolicyTime;

    /**
     * Enable batching of single Point writes to speed up writes significantly. If either number of points written or
     * flushDuration time limit is reached, a batch write is issued.
     * Note that batch processing needs to be explicitly stopped before the application is shutdown.
     * To do so call disableBatch().
     */
    private boolean enableBatch = false;

    /**
     * the number of actions to collect
     */
    private int batchAction = 2000;

    /**
     * Jitters the batch flush interval by a random amount(milliseconds). This is primarily to avoid
     * large write spikes for users running a large number of client instances.
     * ie, a jitter of 500ms and flush duration 1000ms means flushes will happen every 1000-1500ms.
     */
    private int jitterDuration = 500;

    /**
     * the time to wait at most (milliseconds).
     */
    private int flushDuration = 1000;

    /**
     * udp port default 8089
     */
    private int udpPort = 8089;

    /**
     * http connection timeout,Time unit second
     */
    private int connectTimeout = 10;

    /**
     * http read timeout,Time unit second
     */
    private int readTimeout = 30;

    /**
     * http write timeout,Time unit second
     */
    private int writeTimeout = 10;

    /**
     * gzip compression for HTTP requests
     */
    private boolean gzip = false;

    public InfluxProperties() {
    }

    public InfluxProperties(String url, String username, String password, String database, String retentionPolicy, String retentionPolicyTime, boolean enableBatch, int batchAction, int jitterDuration, int flushDuration, int udpPort, int connectTimeout, int readTimeout, int writeTimeout, boolean gzip) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.retentionPolicyTime = retentionPolicyTime;
        this.enableBatch = enableBatch;
        this.batchAction = batchAction;
        this.jitterDuration = jitterDuration;
        this.flushDuration = flushDuration;
        this.udpPort = udpPort;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.gzip = gzip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getRetentionPolicyTime() {
        return retentionPolicyTime;
    }

    public void setRetentionPolicyTime(String retentionPolicyTime) {
        this.retentionPolicyTime = retentionPolicyTime;
    }

    public boolean isEnableBatch() {
        return enableBatch;
    }

    public void setEnableBatch(boolean enableBatch) {
        this.enableBatch = enableBatch;
    }

    public int getBatchAction() {
        return batchAction;
    }

    public void setBatchAction(int batchAction) {
        this.batchAction = batchAction;
    }

    public int getJitterDuration() {
        return jitterDuration;
    }

    public void setJitterDuration(int jitterDuration) {
        this.jitterDuration = jitterDuration;
    }

    public int getFlushDuration() {
        return flushDuration;
    }

    public void setFlushDuration(int flushDuration) {
        this.flushDuration = flushDuration;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public boolean isGzip() {
        return gzip;
    }

    public void setGzip(boolean gzip) {
        this.gzip = gzip;
    }
}
