package com.mobaijun.swagger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: SwaggerConfiguration
 * 类描述：
 *
 * @author MoBaiJun 2022/4/26 14:57
 */
@Configuration
@ConditionalOnProperty(name = "swagger.enable", matchIfMissing = true)
public class SwaggerConfiguration {
}
