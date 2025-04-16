/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.json.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;

/**
 * 全局日期格式化配置类，统一处理 JSON 序列化时 {@link java.util.Date}、
 * {@link java.time.LocalDate} 和 {@link java.time.LocalDateTime} 类型的时间格式。
 *
 * <p>该配置类使用 Jackson 提供的自定义构建器，在 Spring Boot 启动时自动注入。
 * 可以通过配置项 {@code spring.jackson.date-format} 自定义时间格式，默认为 {@code yyyy-MM-dd HH:mm:ss}。</p>
 *
 * <p>该类支持以下时间类型格式化：</p>
 * <ul>
 *     <li>{@code java.util.Date}</li>
 *     <li>{@code java.time.LocalDate}</li>
 *     <li>{@code java.time.LocalDateTime}</li>
 * </ul>
 *
 * <p>示例输出：</p>
 * <pre>{@code
 * {
 *     "createdDate": "2025-04-15",
 *     "lastModifiedDateTime": "2025-04-15 17:45:30"
 * }
 * }</pre>
 *
 * @author mobaijun
 * @version 1.0
 */
@JsonComponent
public class DateFormatConfig {

    /**
     * LocalDate 格式化使用的固定模式。
     */
    private static final String LOCAL_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 全局时间格式，默认值为 yyyy-MM-dd HH:mm:ss，可通过配置文件覆盖：
     * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
     */
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateTimePattern;

    /**
     * 配置 Jackson 的自定义时间格式处理器。
     * 适用于 Date、LocalDate、LocalDateTime 的全局统一格式化。
     *
     * @param localDateTimeSerializer LocalDateTime 的序列化器
     * @param localDateSerializer     LocalDate 的序列化器
     * @return Jackson2ObjectMapperBuilderCustomizer 实例
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer globalDateFormatCustomizer(
            LocalDateTimeSerializer localDateTimeSerializer,
            LocalDateSerializer localDateSerializer
    ) {
        return builder -> {
            // 设置时区为 UTC（也可改为 TimeZone.getDefault() 使用本地时区）
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat(dateTimePattern);
            df.setTimeZone(tz);

            builder.failOnEmptyBeans(false)
                    .failOnUnknownProperties(false)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .dateFormat(df)
                    .serializerByType(LocalDateTime.class, localDateTimeSerializer)
                    .serializerByType(LocalDate.class, localDateSerializer);
        };
    }

    /**
     * 提供 LocalDateTime 的 Jackson 序列化器。
     *
     * @return LocalDateTimeSerializer 实例，使用配置的全局格式
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimePattern));
    }

    /**
     * 提供 LocalDate 的 Jackson 序列化器。
     *
     * @return LocalDateSerializer 实例，使用固定的 yyyy-MM-dd 格式
     */
    @Bean
    public LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN));
    }
}