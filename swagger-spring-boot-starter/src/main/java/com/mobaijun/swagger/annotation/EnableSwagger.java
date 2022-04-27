package com.mobaijun.swagger.annotation;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.mobaijun.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MoBaiJun 2022/4/26 9:26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import({BeanValidatorPluginsConfiguration.class, SwaggerAutoConfiguration.class})
public @interface EnableSwagger {
}
