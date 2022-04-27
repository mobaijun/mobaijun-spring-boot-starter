package com.mobaijun.influxdb.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InfluxProperties
 * 类描述： 配置文件
 *
 * @author MoBaiJun 2022/4/27 8:53
 */
@ConfigurationProperties(InfluxProperties.PREFIX)
@EnableConfigurationProperties(InfluxProperties.class)
public class InfluxProperties {

    /**
     * 映射配置文件路径
     */
    public static final String PREFIX = "spring.influxdb";

    /**
     * 链接地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库
     */
    private String database;

    /**
     * 存储策略
     */
    private String retentionPolicy;

    /**
     * 数据过期时间
     */
    private String retentionPolicyTime;

    public InfluxProperties() {
    }

    public InfluxProperties(String url, String username, String password, String database, String retentionPolicy, String retentionPolicyTime) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.retentionPolicyTime = retentionPolicyTime;
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
}
