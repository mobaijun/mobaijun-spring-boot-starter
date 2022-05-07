package com.mobaijun.mongo.annotation;

import com.mobaijun.mongo.config.MongoAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * AnnotationName: EnableMongo
 * 注解描述： 启动注解
 *
 * @author MoBaiJun 2022/5/6 15:57
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MongoAutoConfiguration.class})
public @interface EnableMongo {
}
