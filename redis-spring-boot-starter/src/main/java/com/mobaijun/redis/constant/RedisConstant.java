package com.mobaijun.redis.constant;

import java.time.Duration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: RedisConstant
 * class description： Redis 通用常量
 *
 * @author MoBaiJun 2022/5/16 13:25
 */
public class RedisConstant {

    /**
     * 默认缓存过期时间 30 分钟
     */
    public static final Long MINUTES = 30L;

    /**
     * 默认缓存过期时间 1 天
     */
    public static final Long DAY = 1L;

    /**
     * 24小时
     */
    public static final Duration ONE_DAY = Duration.ofDays(1);

    /**
     * 一周(7天)
     */
    public static final Duration WEEK_DAY = Duration.ofDays(7);

    /**
     * 30 分钟
     */
    public static final Duration THREE_MINUTES = Duration.ofMinutes(30);

    /**
     * 60 分钟
     */
    public static final Duration SIXTS_MINUTES = Duration.ofMinutes(60);

    /**
     * 60 秒
     */
    public static final Duration SIXTS_SECONDS = Duration.ofSeconds(60);
}
