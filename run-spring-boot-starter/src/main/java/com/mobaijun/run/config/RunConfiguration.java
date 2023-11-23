package com.mobaijun.run.config;

import com.mobaijun.run.listener.RunCommandRunner;
import com.mobaijun.run.prop.RunProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(prefix = "run.web-server", name = "enabled", havingValue = "true")
public class RunConfiguration {

    private final RunProperties runProperties;

    public RunConfiguration(RunProperties runProperties) {
        this.runProperties = runProperties;
    }

    @Bean
    public RunProperties getRunProperties() {
        return new RunProperties();
    }

    @Bean
    public RunCommandRunner getRunCommandRunner() {
        return new RunCommandRunner(runProperties);
    }
}
