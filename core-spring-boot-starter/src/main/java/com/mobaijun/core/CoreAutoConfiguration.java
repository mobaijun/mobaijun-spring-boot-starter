package com.mobaijun.core;

import com.mobaijun.core.config.ApplicationServerConfig;
import com.mobaijun.core.spring.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: CoreAutoConfiguration<br>
 * class description: 自动注入类<br>
 *
 * @author MoBaiJun 2022/12/7 14:17
 */
@Configuration
public class CoreAutoConfiguration {

    /**
     * 注入应用程序配置类
     *
     * @return ApplicationConfig
     */
    @Bean
    public ApplicationServerConfig applicationConfig() {
        return new ApplicationServerConfig();
    }

    /**
     * Spring 上下文工具类
     *
     * @return SpringContextUtils
     */
    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }
}
