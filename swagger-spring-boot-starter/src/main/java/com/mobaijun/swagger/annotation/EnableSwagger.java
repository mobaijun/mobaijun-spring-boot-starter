package com.mobaijun.swagger.annotation;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.mobaijun.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MoBaiJun 2022/4/26 9:26
 */
@Documented
@EnableOpenApi
@EnableKnife4j
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Import({BeanValidatorPluginsConfiguration.class, SwaggerAutoConfiguration.class})
public @interface EnableSwagger {
}
