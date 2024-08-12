package com.mobaijun.core.config;

import jakarta.validation.Validator;
import java.util.Properties;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Description: [校验框架配置类]
 * Author: [mobaijun]
 * Date: [2024/8/12 9:49]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
public class ValidatorConfig {

    /**
     * 配置校验框架 快速返回模式
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        try (LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean()) {
            // 国际化
            factoryBean.setValidationMessageSource(messageSource);
            // 设置使用 HibernateValidator 校验器
            factoryBean.setProviderClass(HibernateValidator.class);
            Properties properties = new Properties();
            // 设置 快速异常返回
            properties.setProperty("hibernate.validator.fail_fast", "true");
            factoryBean.setValidationProperties(properties);
            // 加载配置
            factoryBean.afterPropertiesSet();
            return factoryBean.getValidator();
        }
    }
}
