package com.mobaijun.influxdb.core.enums;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * EnumName: Function
 * 枚举描述：常用的聚合函数枚举
 *
 * @author MoBaiJun 2022/4/29 11:47
 */
public enum Function {

    SUM("sum", "累加"),
    LAST("last", "最后一条数据"),
    MEAN("mean", "平均数");
    private final String tag;

    private final String content;

    Function(String tag, String content) {
        this.tag = tag;
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }
}
