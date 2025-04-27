package com.mobaijun.easyexcel;

import com.mobaijun.easyexcel.properties.ExcelProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Description: [excel 配置类]
 * Author: [mobaijun]
 * Date: [2025/4/26 13:44]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
public class EasyExceclConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(ExcelProperties.PREFIX)
    public ExcelProperties excelProperties() {
        return new ExcelProperties();
    }
}
