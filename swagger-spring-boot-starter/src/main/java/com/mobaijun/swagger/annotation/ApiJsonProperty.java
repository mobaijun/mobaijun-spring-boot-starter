package com.mobaijun.swagger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MoBaiJun 2022/4/26 9:25
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonProperty {

    /**
     * 参数描述
     *
     * @return String
     */
    String value() default "";

    /**
     * 字段名称
     *
     * @return String
     */
    String name();

    /**
     * 数据类型 （支持string、integer、long、double、date）
     *
     * @return String
     */
    String dataType() default "string";

    /**
     * 参数是否必填
     *
     * @return boolean
     */
    boolean required() default false;

    /**
     * 示例
     *
     * @return String
     */
    String example() default "";
}
