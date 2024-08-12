package com.mobaijun.dubbo.config;

import com.mobaijun.core.factory.YamlPropertySourceFactory;
import com.mobaijun.dubbo.properrties.DubboCustomProperties;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * Description: [dubbo 配置类]
 * Author: [mobaijun]
 * Date: [2024/8/5 11:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
@EnableConfigurationProperties(DubboCustomProperties.class)
@PropertySource(value = "classpath:common-dubbo.yml", factory = YamlPropertySourceFactory.class)
public class DubboConfiguration {

    /**
     * dubbo自定义IP注入(避免IP不正确问题)
     */
    @Bean
    public BeanFactoryPostProcessor customBeanFactoryPostProcessor() {
        return new CustomBeanFactoryPostProcessor();
    }
}
