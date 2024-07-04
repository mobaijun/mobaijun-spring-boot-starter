/*
 * Copyright (C) 2022 www.mobaijun.com
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
package com.mobaijun.influxdb.util;

import com.mobaijun.influxdb.core.constant.Constant;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: CommonUtils
 * 类描述： 工具类
 *
 * @author MoBaiJun 2022/4/29 14:15
 */
public class CommonUtils {

    /**
     * 时间转换 Instant字符串时间转 LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime parseStringToLocalDateTime(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * localDateTime 转 Instant
     *
     * @param localDateTime localDateTime
     * @return Instant
     */
    public static Instant parseLocalDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }


    /**
     * 下划线转驼峰
     *
     * @param str String
     * @return String
     */
    public static String lineToHump(String str) {
        Matcher matcher = Constant.LINE_PATTERN.matcher(str.toLowerCase());
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str str
     * @return String
     */
    public static String humpToLine(String str) {
        Matcher matcher = Constant.HUMP_PATTERN.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, Constant.UNDERSCORE + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}