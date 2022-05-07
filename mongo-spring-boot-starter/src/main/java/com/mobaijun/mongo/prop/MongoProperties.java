package com.mobaijun.mongo.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: MongoProperties
 * 类描述： 配置文件
 *
 * @author MoBaiJun 2022/5/6 15:56
 */
@ConfigurationProperties(MongoProperties.PREFIX)
@EnableConfigurationProperties(MongoProperties.class)
public class MongoProperties {

    public static final String PREFIX = "spring.mongo";

    /**
     * 每个主机的最小连接数
     */
    private Integer minConnectionPerHost = 0;

    /**
     * 每个主机的最大连接数
     */
    private Integer maxConnectionPerHost = 100;

    /**
     * 线程允许阻塞连接乘数
     */
    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;

    /**
     * 服务器选择超时
     */
    private Integer serverSelectionTimeout = 30000;

    /**
     * 最长等待时间
     */
    private Integer maxWaitTime = 120000;

    /**
     * 最大连接空闲时间
     */
    private Integer maxConnectionIdleTime = 0;

    /**
     * 最长连接寿命
     */
    private Integer maxConnectionLifeTime = 0;

    /**
     * 连接超时
     */
    private Integer connectTimeout = 10000;

    /**
     * 套接字超时
     */
    private Integer socketTimeout = 0;

    /**
     * 套接字保持活跃
     */
    private Boolean socketKeepAlive = false;

    /**
     * 启用 SSL
     */
    private Boolean sslEnabled = false;

    /**
     * ssl 允许的主机名无效
     */
    private Boolean sslInvalidHostNameAllowed = false;

    /**
     * 始终使用 M Bean
     */
    private Boolean alwaysUseMBeans = false;

    /**
     * 心跳频率
     */
    private Integer heartbeatFrequency = 10000;

    /**
     * 最小心跳频率
     */
    private Integer minHeartbeatFrequency = 500;

    /**
     * 心跳连接超时
     */
    private Integer heartbeatConnectTimeout = 20000;

    /**
     * 心跳套接字超时
     */
    private Integer heartbeatSocketTimeout = 20000;

    /**
     * 本地阈值
     */
    private Integer localThreshold = 15;
}
