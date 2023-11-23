package com.mobaijun.run.config;

import com.mobaijun.run.listener.RunCommandRunner;
import com.mobaijun.run.prop.RunProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: [run 配置]
 * Author: [mobaijun]
 * Date: [2023/11/23 11:26]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
@EnableConfigurationProperties(RunProperties.class)
public class RunConfiguration {

    @Bean
    public RunProperties getRunProperties() {
        return new RunProperties();
    }

    @Bean
    public RunCommandRunner getRunCommandRunner() {
        return new RunCommandRunner(getRunProperties());
    }
}
