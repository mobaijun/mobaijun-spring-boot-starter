package com.mobaijun.mongo.config;

import com.mobaijun.mongo.prop.MongoProperties;
import com.mongodb.client.MongoClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: MongoAutoConfiguration
 * 类描述： mongo 配置类
 *
 * @author MoBaiJun 2022/5/6 15:55
 */
@Configuration
@ConditionalOnClass(MongoClient.class)
@EnableConfigurationProperties(MongoProperties.class)
@ConditionalOnMissingBean(type = "org.springframework.data.mongodb.MongoDbFactory")
public class MongoAutoConfiguration {
}
