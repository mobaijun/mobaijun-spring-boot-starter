package com.mobaijun.redis.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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

    public static final String PREFIX = "spring.redis.lettuce";

    /**
     * 是否启用，默认启用
     */
    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
