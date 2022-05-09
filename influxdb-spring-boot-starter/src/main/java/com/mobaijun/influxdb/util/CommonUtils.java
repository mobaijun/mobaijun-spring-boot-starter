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
        DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(time, df);
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
        StringBuffer sb = new StringBuffer();
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
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, Constant.UNDERSCORE + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
