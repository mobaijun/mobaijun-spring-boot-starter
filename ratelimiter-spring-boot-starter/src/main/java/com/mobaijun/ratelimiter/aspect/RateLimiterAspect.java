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
package com.mobaijun.ratelimiter.aspect;

import com.mobaijun.ratelimiter.annotanion.RateLimiter;
import com.mobaijun.ratelimiter.enums.LimiterMode;
import com.mobaijun.ratelimiter.exception.RateLimiterException;
import com.mobaijun.ratelimiter.util.SpelParser;
import com.mobaijun.redisson.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Description: [限流处理]
 * Author: [mobaijun]
 * Date: [2024/11/12 10:25]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Aspect
public class RateLimiterAspect {

    /**
     * 日志记录器，用于记录限流相关的日志信息
     */
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    /**
     * 用于判断IP地址是否为空的常量
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 限流切面方法，在带有@RateLimiter注解的方法执行前进行拦截
     *
     * @param joinPoint   连接点，包含目标方法的相关信息
     * @param rateLimiter 限流注解，包含限流配置
     * @throws com.mobaijun.ratelimiter.exception.RateLimiterException 当达到限流条件时抛出此异常
     */
    @Before("@annotation(rateLimiter)")
    public void interceptor(JoinPoint joinPoint, RateLimiter rateLimiter) throws RateLimiterException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        // 获取客户端IP地址
        String ip = getClientIp(rateLimiter);
        // 使用传入的 rateLimiter 参数和方法上的RateLimiter注解
        boolean limitFlag = processRateLimit(rateLimiter, method, args, ip);

        if (limitFlag) {
            // 达到限流条件，抛出异常
            throw new RateLimiterException("触发了滥用检测机制，请稍候再试。", ip);
        }
    }

    /**
     * 处理限流逻辑
     *
     * @param limitAnnotation 当前RateLimiter注解
     * @param method          当前方法
     * @param args            方法参数
     * @param ip              IP地址
     * @return 是否触发限流条件
     */
    private boolean processRateLimit(RateLimiter limitAnnotation, Method method, Object[] args, String ip) {
        String project = limitAnnotation.project();
        String resolveLimitKey = SpelParser.resolveLimitKey(limitAnnotation.key(), method, args);
        // 根据不同的限流模式进行处理
        return switch (limitAnnotation.limitMode()) {
            case KEY -> checkRateLimit(limitAnnotation, project, resolveLimitKey, null);
            case COMBINATION, IP -> checkRateLimit(limitAnnotation, project, resolveLimitKey, ip);
        };
    }

    /**
     * 获取客户端的IP地址，如果需要
     *
     * @param limitAnnotation 当前的RateLimiter注解
     * @return 客户端IP地址
     */
    private String getClientIp(RateLimiter limitAnnotation) {
        if (limitAnnotation.limitMode().equals(LimiterMode.IP) || limitAnnotation.limitMode().equals(LimiterMode.COMBINATION)) {
            return getIpAddress();
        }
        return null;
    }

    /**
     * 检查是否超出限流次数，使用 Redisson 提供的 rateLimiter 方法进行限流
     *
     * @param limitAnnotation 限流注解
     * @param project         项目名称
     * @param limitKey        限流资源的 key
     * @param ip              客户端 IP
     * @return true 如果超出限流次数，否则 false
     */
    private boolean checkRateLimit(RateLimiter limitAnnotation, String project, String limitKey, String ip) {

        // 构造 Redis 键（根据限流模式决定是否使用 IP）
        String key = limitAnnotation.prefix() + project + limitKey;

        if (limitAnnotation.limitMode() == LimiterMode.COMBINATION || limitAnnotation.limitMode() == LimiterMode.IP) {
            // 如果是组合模式或按IP限流，则加入IP作为key的一部分
            key += ip;
        }

        // 调用封装好的 rateLimiter 方法执行限流操作
        long availablePermits = RedisUtil.rateLimiter(
                key,
                limitAnnotation.limitMode() == LimiterMode.IP ? RateType.PER_CLIENT : RateType.OVERALL,
                limitAnnotation.keyLimitCount(),
                // 使用 period 作为限流周期，单位秒
                Duration.ofSeconds(limitAnnotation.period()),
                // 设置过期时间
                Duration.ofMinutes(limitAnnotation.expireTime())
        );

        // 记录日志
        log.info("尝试访问: project={}, key={}, ip={}, 当前可用令牌数={}", project, limitKey, ip, availablePermits);

        // 判断是否超出限流次数：如果返回 -1，表示限流失败；否则检查可用令牌数
        return availablePermits == -1L || availablePermits == 0;
    }

    /**
     * 获取客户端的IP地址
     * 通过多个HTTP头信息获取真实IP地址，考虑了代理的情况
     *
     * @return 返回客户端的IP地址
     */
    private String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}