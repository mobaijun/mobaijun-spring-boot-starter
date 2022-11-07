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
     * 一周(7天)
     */
    public static final Duration WEEK_DAY = Duration.ofDays(7);

    /**
     * 5天
     */
    public static final Duration FIVE_DAY = Duration.ofDays(5);

    /**
     * 三天
     */
    public static final Duration THREE_DAY = Duration.ofDays(3);

    /**
     * 24小时
     */
    public static final Duration ONE_DAY = Duration.ofHours(24);

    /**
     * 60 分钟
     */
    public static final Duration SIXTY_MINUTES = Duration.ofMinutes(60);

    /**
     * 30 分钟
     */
    public static final Duration THIRTY_MINUTES = Duration.ofMinutes(30);

    /**
     * 15 分钟
     */
    public static final Duration FIFTEEN_MINUTES = Duration.ofMinutes(15);

    /**
     * 10 分钟
     */
    public static final Duration TEN_MINUTES = Duration.ofMinutes(10);

    /**
     * 5 分钟
     */
    public static final Duration FIVE_MINUTES = Duration.ofMinutes(5);

    /**
     * 3 分钟
     */
    public static final Duration THREEE_MINUTES = Duration.ofMinutes(3);

    /**
     * 60 秒
     */
    public static final Duration SIXTS_SECONDS = Duration.ofSeconds(60);
}