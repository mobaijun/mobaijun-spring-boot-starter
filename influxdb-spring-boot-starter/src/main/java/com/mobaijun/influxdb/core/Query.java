package com.mobaijun.influxdb.core;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.core.model.QueryModel;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Query
 * 类描述： 查询
 *
 * @author MoBaiJun 2022/4/29 14:04
 */
public class Query extends BaseQuery {

    /**
     * 构造条件
     *
     * @param model QueryModel
     * @return String
     */
    public static String build(QueryModel model) {
        Objects.requireNonNull(model.getMeasurement(), Constant.QUERY_MODEL_MEASUREMENT);
        StringBuilder query = new StringBuilder();
        query.append(Constant.SELECT).append(model.getSelect());
        query.append(Constant.FROM).append(model.getMeasurement());
        if (!ObjectUtils.isEmpty(model.getWhere())) {
            query.append(Constant.WHERE).append(model.getWhere());
        }
        if (!ObjectUtils.isEmpty(model.getGroup())) {
            query.append(Constant.GROUP_BY).append(model.getGroup());
        }
        if (!ObjectUtils.isEmpty(model.getOrder())) {
            query.append(Constant.ORDER_BY_TIME).append(model.getOrder());
        }
        if (!ObjectUtils.isEmpty(model.getPageNum()) && !ObjectUtils.isEmpty(model.getPageSize())) {
            query.append(Constant.SPACE).append(model.getPageQuery());
        }
        if (model.getUseTimeZone()) {
            query.append(Constant.SPACE).append(model.getTimeZone());
        }
        String sql = query.toString();
        log.info(sql);
        return sql;
    }

    /**
     * count Field 字段
     *
     * @param field String
     * @return StringBuilder
     */
    public static StringBuilder count(String field) {
        return new StringBuilder()
                .append(Constant.CONUNT_POSITIVE_BRACKETS)
                .append(Constant.DELIMITER)
                .append(field)
                .append(Constant.DELIMITER)
                .append(Constant.BACK_BRACKETS);
    }


    /**
     * 聚合函数构建
     *
     * @param tag   tag
     * @param field field
     * @return StringBuilder
     */
    public static StringBuilder funcAggregate(String tag, String field) {
        return new StringBuilder()
                .append(tag)
                .append(Constant.POSITIVE_BRACKETS)
                .append(Constant.DELIMITER)
                .append(field)
                .append(Constant.DELIMITER)
                .append(Constant.BACK_BRACKETS)
                .append(Constant.AS)
                .append(Constant.DELIMITER)
                .append(field)
                .append(Constant.DELIMITER);
    }
}
