package com.mobaijun.influxdb.annotation;

import com.mobaijun.influxdb.core.enums.Function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * AnnotationName: Aggregate
 * 注解描述：influxdb 常用聚合函数字段注解
 *
 * @author MoBaiJun 2022/4/29 11:41
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aggregate {

    /**
     * 字段名
     */
    String value();

    /**
     * 字段使用的聚合函数
     */
    Function tag();
}
