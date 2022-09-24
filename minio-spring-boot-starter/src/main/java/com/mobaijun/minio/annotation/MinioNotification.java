package com.mobaijun.minio.annotation;

import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * software：IntelliJ IDEA 2022.1
 * annotation name: MinioNotification
 * annotation description：MinioNotification
 *
 * @author MoBaiJun 2022/9/20 8:41
 */
@Async
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinioNotification {

    /**
     * All events that the method handler should receive. Values defined
     * in <a href="https://docs.min.io/docs/minio-bucket-notification-guide.html">Minio documentation</a> are allowed.
     */
    String[] value();

    /**
     * Prefix of the items that should be handled
     */
    String prefix() default "";

    /**
     * Suffix of the items that should be handled
     */
    String suffix() default "";
}
