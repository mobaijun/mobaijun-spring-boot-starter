/*
 * Copyright (C) 2022 [www.mobaijun.com]
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
package com.mobaijun.ratelimiter.annotanion;

import com.mobaijun.ratelimiter.enums.LimiterMode;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: [限流注解]
 * Author: [mobaijun]
 * Date: [2024/11/12 10:21]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(RateLimiters.class)
public @interface RateLimiter {

    /**
     * 限流键的前缀，用于生成 Redis 中存储限流信息的键。
     * <p>
     * 默认值为 "rate_limiter_"，可以根据实际业务场景修改该值。
     * 例如，可以为不同模块或业务类型设置不同的前缀，避免 Redis 键冲突。
     * </p>
     *
     * @return 限流前缀
     */
    String prefix() default "rate_limiter:";

    /**
     * 限流所属模块的名称。通常用于标识不同的业务模块或系统。
     * <p>
     * 例如，"user_service" 表示用户服务模块，可以根据模块名称和业务需求来配置限流策略。
     * </p>
     *
     * @return 模块名称
     */
    String project() default "project:";

    /**
     * 限流的资源的唯一标识符，通常是 API 路径或其他资源标识符。
     * <p>
     * 例如，可以设置为 API 路径 "/login"，或者资源名称 "user_register"，根据业务需求决定。
     * </p>
     *
     * @return 限流资源的 key
     */
    String key() default "api:";

    /**
     * 限流周期，即时间窗口的长度，单位为秒。
     * <p>
     * 例如，`period = 30` 表示限流周期为 30 秒，即每 30 秒为一个时间窗口进行限流控制。
     * </p>
     *
     * @return 限流周期，单位为秒
     */
    int period() default 30;

    /**
     * 单个资源（由 `key` 标识）在给定时间段内最多允许的访问次数。
     * <p>
     * 例如，`keyLimitCount = 900` 表示在 30 秒内，该资源最多允许 900 次访问请求。
     * </p>
     *
     * @return 单个资源的最大访问次数
     */
    int keyLimitCount() default 900;

    /**
     * 限速模式，决定了限流的策略。可以选择以下三种模式：
     * <ul>
     *     <li><b>IP</b>：按客户端 IP 限流，每个 IP 限制访问次数。</li>
     *     <li><b>KEY</b>：按指定资源的 Key 限流，针对单一资源进行访问控制。</li>
     *     <li><b>COMBINATION</b>：结合 IP 和资源 Key 进行限流，适用于更精细的访问控制。</li>
     * </ul>
     *
     * @return 限速模式
     */
    LimiterMode limitMode() default LimiterMode.IP;

    /**
     * 限流键的过期时间，单位为分钟。
     * <p>
     * 例如，`expireTime = 5` 表示限流键在 5 分钟后过期。默认值为 5 分钟。
     * </p>
     *
     * @return 限流键的过期时间，单位为分钟
     */
    int expireTime() default 5;
}