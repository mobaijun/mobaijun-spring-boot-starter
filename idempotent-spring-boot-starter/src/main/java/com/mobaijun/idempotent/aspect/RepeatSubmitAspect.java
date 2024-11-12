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
package com.mobaijun.idempotent.aspect;

import com.mobaijun.common.exception.ServiceException;
import com.mobaijun.common.result.R;
import com.mobaijun.core.util.MessageUtil;
import com.mobaijun.idempotent.annotation.RepeatSubmit;
import com.mobaijun.redisson.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description: [防止重复提交(参考美团GTIS防重系统)]
 * Author: [mobaijun]
 * Date: [2024/11/12 9:08]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Aspect
public class RepeatSubmitAspect {

    /**
     * 防重提交 redis key
     */
    private static final String REPEAT_SUBMIT_KEY = "global:repeat_submit:";

    /**
     * 使用ThreadLocal来存储每个请求的唯一key
     */
    private static final ThreadLocal<String> KEY_CACHE = new ThreadLocal<>();

    @Before("@annotation(repeatSubmit)")
    public void doBefore(JoinPoint point, RepeatSubmit repeatSubmit) {
        long interval = repeatSubmit.timeUnit().toMillis(repeatSubmit.interval());
        if (interval < 1000) {
            throw new ServiceException("重复提交间隔时间不能小于1秒");
        }

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String nowParams = argsArrayToString(point.getArgs());
        String submitKey = REPEAT_SUBMIT_KEY + request.getServletPath() + ":" + request.getRemoteAddr() + ":" + nowParams;

        if (RedisUtil.setObjIfAbsent(submitKey, "", Duration.ofMillis(interval))) {
            KEY_CACHE.set(submitKey);
        } else {
            String message = resolveMessage(repeatSubmit.message());
            throw new ServiceException(message);
        }
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint    切点
     * @param repeatSubmit 防重复提交注解
     * @param jsonResult   请求结果
     */
    @AfterReturning(pointcut = "@annotation(repeatSubmit)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Object jsonResult) {
        if (!(jsonResult instanceof R<?> r) || r.getCode() != 200) {
            clearCacheKey();
        }
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint    切点
     * @param repeatSubmit 防重复提交注解
     * @param e            异常
     */
    @AfterThrowing(value = "@annotation(repeatSubmit)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Exception e) {
        clearCacheKey();
    }

    /**
     * 清理当前请求的缓存 key
     */
    private void clearCacheKey() {
        String cacheKey = KEY_CACHE.get();
        if (cacheKey != null) {
            RedisUtil.deleteObj(cacheKey);
            KEY_CACHE.remove();
        }
    }

    /**
     * 将方法参数拼接成字符串，用于唯一标识请求
     *
     * @param paramsArray 方法参数数组
     * @return 参数的字符串拼接结果
     */
    private String argsArrayToString(Object[] paramsArray) {
        if (ObjectUtils.isEmpty(paramsArray)) {
            return "";
        }
        return Arrays.stream(paramsArray)
                .filter(Objects::nonNull)
                .filter(o -> !isFilterObject(o))
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    /**
     * 判断对象是否需要过滤
     *
     * @param o 需要判断的对象
     * @return true 如果是需要过滤的对象；否则返回 false
     */
    public boolean isFilterObject(final Object o) {
        if (o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult) {
            return true;
        }

        if (o instanceof Collection) {
            return ((Collection<?>) o).stream().anyMatch(value -> value instanceof MultipartFile);
        }

        if (o instanceof Map) {
            return ((Map<?, ?>) o).values().stream().anyMatch(value -> value instanceof MultipartFile);
        }

        return o.getClass().isArray() && MultipartFile.class.isAssignableFrom(o.getClass().getComponentType());
    }

    /**
     * 解析消息内容，如果是以“{”和“}”包裹则从资源文件获取消息
     *
     * @param message 消息内容
     * @return 解析后的消息
     */
    private String resolveMessage(String message) {
        if (message.startsWith("{") && message.endsWith("}")) {
            return MessageUtil.message(message.substring(1, message.length() - 1));
        }
        return message;
    }
}