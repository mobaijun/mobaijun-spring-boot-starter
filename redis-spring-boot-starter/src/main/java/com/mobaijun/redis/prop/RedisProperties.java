package com.mobaijun.redis.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: RedisProperties
 * 类描述： redis 配置文件
 *
 * @author MoBaiJun 2022/4/28 15:53
 */
@ConfigurationProperties(RedisProperties.PREFIX)
public class RedisProperties {
    public static final String PREFIX = "spring.redis";
}
