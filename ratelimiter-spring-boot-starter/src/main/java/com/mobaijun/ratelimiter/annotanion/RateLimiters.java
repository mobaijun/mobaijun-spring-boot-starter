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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标记方法支持多个限流配置。
 * <p>
 * 使用此注解可以为方法指定多个 {@link RateLimiter} 配置，每个 {@link RateLimiter} 可以定义不同的限流策略，如基于Key、IP或组合方式的限流。
 * 该注解可以用于需要针对多个场景（例如不同的限流方式）进行限流控制的场景。
 * </p>
 * <p>
 * 示例：
 * <pre>
 *     @RateLimiters({
 *         @RateLimiter(limitMode = LimiterMode.KEY, key = "#param1", period = 60, count = 100),
 *         @RateLimiter(limitMode = LimiterMode.IP, period = 60, count = 50)
 *     })
 *     public void someMethod(String param1) {
 *         // 方法实现
 *     }
 * </pre>
 * </p>
 *
 * @see RateLimiter
 * @see com.mobaijun.ratelimiter.enums.LimiterMode
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiters {

    /**
     * 一个方法可以有多个 {@link RateLimiter} 配置。
     *
     * @return 限流配置数组
     */
    RateLimiter[] value();
}