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
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
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
     * RedisTemplate，执行 Redis 脚本
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 限流切面方法，在带有@RateLimiter注解的方法执行前进行拦截
     *
     * @param joinPoint   连接点，包含目标方法的相关信息
     * @param rateLimiter 限流注解，包含限流配置
     * @throws RateLimiterException 当达到限流条件时抛出此异常
     */
    @Before("@annotation(rateLimiter)")
    public void interceptor(JoinPoint joinPoint, RateLimiter rateLimiter) throws RateLimiterException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        // 使用传入的 rateLimiter 参数和方法上的RateLimiter注解
        boolean limitFlag = processRateLimit(rateLimiter, method, args);

        if (limitFlag) {
            // 达到限流条件，抛出异常
            throw new RateLimiterException("触发了滥用检测机制，请稍候再试。");
        }
    }

    /**
     * 处理限流逻辑
     *
     * @param limitAnnotation 当前RateLimiter注解
     * @param method          当前方法
     * @param args            方法参数
     * @return 是否触发限流条件
     */
    private boolean processRateLimit(RateLimiter limitAnnotation, Method method, Object[] args) {
        String project = limitAnnotation.project();
        String limitKeySpel = limitAnnotation.key();
        String limitKey = SpelParser.parse(limitKeySpel, method, args);

        // 获取客户端IP地址
        String ip = getClientIp(limitAnnotation);

        // 限流周期
        int limitPeriod = limitAnnotation.period();
        // 单个key的限流次数
        int keyLimitCount = limitAnnotation.keyLimitCount();
        // 单个IP的限流次数
        int ipLimitCount = limitAnnotation.ipLimitCount();

        // 生成Lua脚本
        String luaScript = buildLuaScript();

        // 根据不同的限流模式进行处理
        return switch (limitAnnotation.limitMode()) {
            case KEY -> checkRateLimit(luaScript, limitAnnotation, project, limitKey, keyLimitCount, limitPeriod, null);
            case COMBINATION ->
                    checkRateLimit(luaScript, limitAnnotation, project, limitKey, keyLimitCount, limitPeriod, ip);
            case IP -> checkRateLimit(luaScript, limitAnnotation, project, limitKey, ipLimitCount, limitPeriod, ip);
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
     * 检查当前限流条件是否被触发
     *
     * @param luaScript       生成的Lua脚本
     * @param limitAnnotation 当前的限流注解
     * @param project         项目名称
     * @param limitKey        限流Key
     * @param limitCount      限流阈值
     * @param limitPeriod     限流周期
     * @param ip              客户端IP地址（可为null）
     * @return 是否触发限流条件
     */
    private boolean checkRateLimit(String luaScript, RateLimiter limitAnnotation, String project, String limitKey,
                                   int limitCount, int limitPeriod, String ip) {
        // 构造Redis键集合
        List<String> keys = new ArrayList<>();
        if (limitAnnotation.limitMode().equals(LimiterMode.COMBINATION) || limitAnnotation.limitMode().equals(LimiterMode.IP)) {
            keys.add(limitAnnotation.prefix() + project + limitKey + ip);
        } else {
            keys.add(limitAnnotation.prefix() + project + limitKey);
        }

        // 执行Redis限流脚本
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = redisTemplate.execute(redisScript, keys, limitPeriod);

        // 记录日志
        log.debug("尝试访问: project={}, key={}, ip={}, 当前访问次数={}", project, limitKey, ip, count);

        // 判断是否超过限流次数
        return count != null && count.intValue() > limitCount;
    }

    /**
     * 生成Lua脚本，用于Redis限流操作
     * Lua脚本通过Redis的`incr`命令进行访问次数的统计，并设置过期时间
     *
     * @return 返回Lua脚本字符串
     */
    private String buildLuaScript() {
        return """
                local c
                c = redis.call('get', KEYS[1])
                c = redis.call('incr', KEYS[1])
                if tonumber(c) == 1 then
                redis.call('expire', KEYS[1], ARGV[1])
                end
                return c;""";
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