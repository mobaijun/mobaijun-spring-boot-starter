package com.mobaijun.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: [配置器]
 * Author: [mobaijun]
 * Date: [2023/11/24 10:23]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
public class JasyptConfiguration {

    @Bean()
    public StringEncryptor stringEncryptor() {
        return JasyptUtil.getEncryptor();
    }
}
