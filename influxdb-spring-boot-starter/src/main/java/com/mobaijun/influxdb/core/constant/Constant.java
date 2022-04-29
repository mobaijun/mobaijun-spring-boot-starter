package com.mobaijun.influxdb.core.constant;

import java.util.regex.Pattern;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Constant
 * 类描述： 常用SQL
 *
 * @author MoBaiJun 2022/4/29 14:39
 */
public class Constant {

    /**
     * 创建数据库
     */
    public static final String CREATE_DATABASE = "CREATE DATABASE";

    /**
     * 查询数据库
     */
    public static final String SHOW_DATABASE = "SHOW DATABASE";

    /**
     * enable
     */
    public static final String ENABLE = "enable";

    /**
     *
     */
    public static final String SELECT = "SELECT ";

    /**
     * from
     */
    public static final String FROM = " FROM ";

    /**
     * GROUP 分组
     */
    public static final String GROUP_BY = " GROUP BY ";

    /**
     * ORDER BY排序
     */
    public static final String ORDER_BY_TIME = " ORDER BY time ";

    /**
     * 页大小
     */
    public static final String LIMIT = " LIMIT ";

    /**
     * 页码
     */
    public static final String OFFSET = " OFFSET ";

    /**
     * and
     */
    public static final String AND = " AND ";

    /**
     * 时间条件
     */
    public static final String TIME_AND = " time >= '";

    /**
     * 时间条件
     */
    public static final String AND_TIME = "' and time <= '";

    /**
     * 分号
     */
    public static final String BRANCH = " '";

    /**
     * 通配符
     */
    public static final String ALL = " * ";

    /**
     * 分隔符
     */
    public static final String DELIMITER = "\"";

    /**
     * 等于号
     */
    public static final String EQUAL_SIGN = " = ";

    /**
     * 空格
     */
    public static final String SPACE = " ";

    /**
     * TIME
     */
    public static final String TIME = " time ";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 时区
     */
    public static final String TIME_ZONE = "tz('Asia/Shanghai')";

    /**
     * DELETE_DROM
     */
    public static final String DELETE_DROM = "DELETE FROM";

    /**
     * DELETE_DROM
     */
    public static final String WHERE = " WHERE ";

    /**
     * AS
     */
    public static final String AS = " AS ";

    /**
     * 统计加正括号
     */
    public static final String CONUNT_POSITIVE_BRACKETS = "count(";

    /**
     * 统计
     */
    public static final String CONUNT = "count";


    /**
     * clazz
     */
    public static final String CLAZZ = "clazz";

    /**
     * queryResult
     */
    public static final String QUERY_RESULT = "queryResult";

    /**
     * 正括号
     */
    public static final String POSITIVE_BRACKETS = " ( ";

    /**
     * 反括号
     */
    public static final String BACK_BRACKETS = " ) ";

    /**
     * 下划线
     */
    public static final String UNDERSCORE = "_";

    /**
     * 正则
     */
    public static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    /**
     * 正则
     */
    public static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    /**
     * DeleteModel.Measurement
     */
    public static final String DELETE_MEASUREMENT = "DeleteModel.Measurement";

    /**
     * QueryModel.Measurement
     */
    public static final String QUERY_MODEL_MEASUREMENT = "QueryModel.Measurement";
}
