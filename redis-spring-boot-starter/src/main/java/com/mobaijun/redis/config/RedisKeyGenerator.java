package com.mobaijun.redis.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: RedisKeyGenerator
 * class description：配置 Redis key 生成策略
 *
 * @author MoBaiJun 2022/5/16 13:21
 */
public class RedisKeyGenerator extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return getKeyGenerator();
    }

    /**
     * 配置 Redis key 生成策略
     *
     * @return KeyGenerator
     */
    public static KeyGenerator getKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}
