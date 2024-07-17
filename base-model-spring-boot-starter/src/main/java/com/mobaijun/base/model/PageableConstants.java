package com.mobaijun.base.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Description: [分页常量]
 * Author: [mobaijun]
 * Date: [2024/7/17 15:17]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class PageableConstants {

    /**
     * 排序的 Field 部分的正则
     */
    public static final String SORT_FILED_REGEX = "(([A-Za-z0-9_]{1,10}\\.)?[A-Za-z0-9_]{1,64})";

    /**
     * 排序的 order 部分的正则
     */
    public static final String SORT_FILED_ORDER = "(desc|asc)";

    /**
     * 完整的排序规则正则
     */
    public static final String SORT_REGEX = "^" + PageableConstants.SORT_FILED_REGEX + "(,"
            + PageableConstants.SORT_FILED_ORDER + ")*$";

    /**
     * 默认的当前页数的参数名
     */
    public static final String DEFAULT_PAGE_PARAMETER = "page";

    /**
     * 默认的单页条数的参数名
     */
    public static final String DEFAULT_SIZE_PARAMETER = "size";

    /**
     * 默认的排序参数的参数名
     */
    public static final String DEFAULT_SORT_PARAMETER = "sort";

    /**
     * 默认的最大单页条数
     */
    public static final int DEFAULT_MAX_PAGE_SIZE = 100;

    /**
     * 升序关键字
     */
    public static final String ASC = "asc";

    /**
     * SQL 关键字
     */
    public static final Set<String> SQL_KEYWORDS = new HashSet<>(Arrays.asList("master", "truncate", "insert", "select",
            "delete", "update", "declare", "alter", "drop", "sleep"));
}
