package com.mobaijun.influxdb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * AnnotationName: Count
 * 注解描述：influxdb 分页查询 count 字段注解
 *
 * @author MoBaiJun 2022/4/29 11:42
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Count {

    /**
     * 字段名称
     */
    String value() default "";
}
