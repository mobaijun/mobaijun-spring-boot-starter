package com.mobaijun.seata;

import com.mobaijun.core.factory.YamlPropertySourceFactory;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Description: [分布式食物自动注入类]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:10]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@PropertySource(value = "classpath:seata-config.yml", factory = YamlPropertySourceFactory.class)
@EnableAutoDataSourceProxy(useJdkProxy = true)
@Configuration(proxyBeanMethods = false)
public class SeataAutoConfiguration {
}
