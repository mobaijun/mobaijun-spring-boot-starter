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
package com.mobaijun.redisson.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mobaijun.core.spring.SpringUtil;
import com.mobaijun.redisson.handler.KeyPrefixHandler;
import com.mobaijun.redisson.handler.RedisExceptionHandler;
import com.mobaijun.redisson.properties.RedissonProperties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.CompositeCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.task.VirtualThreadTaskExecutor;

/**
 * Description: [redis 配置类]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:28]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedissonProperties.class)
public class RedisConfiguration {

    /**
     * redisson 配置
     */
    private final RedissonProperties redissonProperties;

    /**
     * 是否是虚拟线程
     *
     * @return 返回值
     */
    public static boolean isVirtual() {
        return Threading.VIRTUAL.isActive(SpringUtil.getBean(Environment.class));
    }

    @Bean
    public RedissonAutoConfigurationCustomizer redissonCustomizer() {
        return config -> {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            ObjectMapper om = new ObjectMapper();
            om.registerModule(javaTimeModule);
            om.setTimeZone(TimeZone.getDefault());
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // 指定序列化输入的类型，类必须是非final修饰的。序列化时将对象全类名一起保存下来
            om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
            TypedJsonJacksonCodec jsonCodec = new TypedJsonJacksonCodec(Object.class, om);
            // 组合序列化 key 使用 String 内容使用通用 json 格式
            CompositeCodec codec = new CompositeCodec(StringCodec.INSTANCE, jsonCodec, jsonCodec);
            config.setThreads(redissonProperties.getThreads())
                    .setNettyThreads(redissonProperties.getNettyThreads())
                    // 缓存 Lua 脚本 减少网络传输(redisson 大部分的功能都是基于 Lua 脚本实现)
                    .setUseScriptCache(true)
                    .setCodec(codec);
            if (isVirtual()) {
                config.setNettyExecutor(new VirtualThreadTaskExecutor("redisson-"));
            }
            RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
            if (Objects.nonNull(singleServerConfig)) {
                // 使用单机模式
                config.useSingleServer()
                        //设置redis key前缀
                        .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                        .setTimeout(singleServerConfig.getTimeout())
                        .setClientName(singleServerConfig.getClientName())
                        .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                        .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                        .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                        .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
            }
            // 集群配置方式 参考下方注释
            RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
            if (Objects.nonNull(clusterServersConfig)) {
                config.useClusterServers()
                        //设置redis key前缀
                        .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                        .setTimeout(clusterServersConfig.getTimeout())
                        .setClientName(clusterServersConfig.getClientName())
                        .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
                        .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
                        .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
                        .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
                        .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
                        .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
                        .setReadMode(clusterServersConfig.getReadMode())
                        .setSubscriptionMode(clusterServersConfig.getSubscriptionMode());
            }
            log.info("================================== 初始化 redis 配置 =================================");
        };
    }

    /**
     * 异常处理器
     */
    @Bean
    public RedisExceptionHandler redisExceptionHandler() {
        return new RedisExceptionHandler();
    }
}
