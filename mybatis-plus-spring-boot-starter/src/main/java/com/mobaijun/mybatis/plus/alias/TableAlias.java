package com.mobaijun.mybatis.plus.alias;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * AnnotationName: TableAlias
 * 注解描述：表别名注解，注解在 entity 上，便于构建带别名的查询条件或者查询列
 *
 * @author MoBaiJun 2022/5/7 16:16
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableAlias {

    /**
     * 当前实体对应的表别名
     * @return String 表别名
     */
    String value();
}