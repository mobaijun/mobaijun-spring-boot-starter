package com.mobaijun.easyexcel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: [批注]
 * Author: [mobaijun]
 * Date: [2024/12/27 9:29]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelNotation {

    /**
     * col index
     */
    int index() default -1;

    /**
     * 批注内容
     */
    String value() default "";
}
