package com.mobaijun.redis.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: RedisProperties
 * 类描述： redis 配置文件
 *
 * @author MoBaiJun 2022/4/28 15:53
 */
@ConfigurationProperties(RedisProperties.PREFIX)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisProperties {

    public static final String PREFIX = "spring.redis";

    /**
     * 是否启用，默认启用
     */
    private boolean enable = true;

    /**
     * 数据库 0-15
     */
    private int database = 0;

    /**
     * url
     */
    private String url;

    /**
     * 链接地址，默认 localhost
     */
    private String host = "localhost";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 端口，默认6379
     */
    private int port = 6379;

    /**
     * ssl 是否加密
     */
    private boolean ssl;

    /**
     * 超时时间
     */
    private Duration timeout;

    /**
     * 链接超时时间配置
     */
    private Duration connectTimeout;

    public RedisProperties() {
    }

    public RedisProperties(boolean enable, int database, String url, String host, String username, String password, int port, boolean ssl, Duration timeout, Duration connectTimeout) {
        this.enable = enable;
        this.database = database;
        this.url = url;
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.ssl = ssl;
        this.timeout = timeout;
        this.connectTimeout = connectTimeout;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
