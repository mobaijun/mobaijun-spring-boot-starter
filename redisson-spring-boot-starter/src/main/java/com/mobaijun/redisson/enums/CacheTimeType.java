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
package com.mobaijun.redisson.enums;

import java.time.Duration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: CacheTimeType
 * class description： Redis 通用常量
 *
 * @author MoBaiJun 2022/5/16 13:25
 */
public enum CacheTimeType {

    /**
     * 默认缓存过期时间 30 分钟
     */
    MINUTES(Duration.ofMinutes(30)),

    /**
     * 默认缓存过期时间 1 天
     */
    DAY(Duration.ofDays(1)),

    /**
     * 一周(7天)
     */
    WEEK_DAY(Duration.ofDays(7)),

    /**
     * 5天
     */
    FIVE_DAY(Duration.ofDays(5)),

    /**
     * 三天
     */
    THREE_DAY(Duration.ofDays(3)),

    /**
     * 24小时
     */
    ONE_DAY(Duration.ofHours(24)),

    /**
     * 60 分钟
     */
    SIXTY_MINUTES(Duration.ofMinutes(60)),

    /**
     * 30 分钟
     */
    THIRTY_MINUTES(Duration.ofMinutes(30)),

    /**
     * 15 分钟
     */
    FIFTEEN_MINUTES(Duration.ofMinutes(15)),

    /**
     * 10 分钟
     */
    TEN_MINUTES(Duration.ofMinutes(10)),

    /**
     * 5 分钟
     */
    FIVE_MINUTES(Duration.ofMinutes(5)),

    /**
     * 3 分钟
     */
    THREEE_MINUTES(Duration.ofMinutes(3)),

    /**
     * 60 秒
     */
    SIXTS_SECONDS(Duration.ofSeconds(60));

    /**
     * 缓存时间
     */
    private final Duration time;

    CacheTimeType(Duration time) {
        this.time = time;
    }

    public Duration getTime() {
        return time;
    }
}