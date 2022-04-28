package com.mobaijun.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobaijun.redis.prop.RedisProperties;
import com.mobaijun.redis.util.RedisLockUtil;
import com.mobaijun.redis.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: RedisAutoConfiguration
 * 类描述： Redis 配置类
 *
 * @author MoBaiJun 2022/4/28 15:52
 */
@Configuration
@ConditionalOnProperty(name = RedisProperties.PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
public class RedisAutoConfiguration {

    private static Logger log = LoggerFactory.getLogger(RedisAutoConfiguration.class);

    @Bean
    public RedisSerializer<String> redisKeySerializer() {
        return RedisSerializer.string();
    }

    @Bean
    public RedisSerializer<Object> redisValueSerializer() {
        return RedisSerializer.json();
    }

    @SuppressWarnings("all")
    @Bean(name = "redisTemplate")
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        log.info("============================ Redis 序列化成功 ============================");
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化(默认采用的是JDK序列化)
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public RedisUtils redisService() {
        return new RedisUtils();
    }

    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public RedisLockUtil redisLockUtil(RedisTemplate<String, Object> redisTemplate) {
        return new RedisLockUtil(redisTemplate);
    }
}
