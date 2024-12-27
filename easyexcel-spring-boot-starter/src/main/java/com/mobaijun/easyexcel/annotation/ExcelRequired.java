package com.mobaijun.easyexcel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Description: [是否必填]
 * Author: [mobaijun]
 * Date: [2024/12/27 9:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelRequired {

    /**
     * col index
     */
    int index() default -1;

    /**
     * 字体颜色
     */
    IndexedColors fontColor() default IndexedColors.RED;
}
