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
     * 前缀
     *
     * @return String
     */
    String prefix() default "rate_limiter_";

    /**
     * 模块的名字
     *
     * @return String
     */
    String project() default "project_";

    /**
     * 资源的key
     *
     * @return String
     */
    String key() default "api_";

    /**
     * 给定的时间段
     * 单位秒
     *
     * @return int
     */
    int period() default 30;

    /**
     * 最多访问限制次数
     *
     * @return int
     */
    int keyLimitCount() default 900;

    /**
     * 最多访问限制次数
     *
     * @return int
     */
    int ipLimitCount() default 15;

    /**
     * 限速模式
     *
     * @return int
     */
    LimiterMode limitMode() default LimiterMode.IP;
}