package com.mobaijun.influxdb.core.model;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.core.enums.Order;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: QueryModel
 * 类描述： 查询model、
 *
 * @author MoBaiJun 2022/4/29 13:57
 */
public abstract class AbstractQueryModel extends BaseModel {

    /**
     * 查询的字段
     */
    private String select;

    /**
     * influxdb仅支持time排序
     * Order.DESC Order.ASC
     */
    private Order order;

    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页的大小
     */
    private Long pageSize;

    /**
     * 默认不使用时区
     */
    private Boolean useTimeZone = false;

    /**
     * 默认时区 Asia/Shanghai
     */
    private String timeZone = "tz('Asia/Shanghai')";

    /**
     * 分组
     */
    private String group;

    public AbstractQueryModel() {

    }

    public AbstractQueryModel(String measurement) {
        super(measurement);
    }

    public StringBuilder getPageQuery() {
        return new StringBuilder()
                .append(Constant.LIMIT)
                .append(pageSize)
                .append(Constant.OFFSET)
                .append((pageNum - 1) * pageSize);
    }

    public String getSelect() {
        if (select.isEmpty() && select.trim().isEmpty()) {
            select = Constant.ALL.trim();
        }
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getUseTimeZone() {
        return useTimeZone;
    }

    public void setUseTimeZone(Boolean useTimeZone) {
        this.useTimeZone = useTimeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
