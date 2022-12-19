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
package com.mobaijun.influxdb.core;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.core.model.AbstractQueryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(Query.class);

    /**
     * 构造条件
     *
     * @param model QueryModel
     * @return String
     */
    public static String build(AbstractQueryModel model) {
        Objects.requireNonNull(model.getMeasurement(), Constant.QUERY_MODEL_MEASUREMENT);
        StringBuilder query = new StringBuilder();
        query.append(Constant.SELECT).append(model.getSelect());
        query.append(Constant.FROM).append(model.getMeasurement());
        if (Objects.nonNull(model.getWhere())) {
            query.append(Constant.WHERE).append(model.getWhere());
        }
        if (Objects.nonNull(model.getGroup())) {
            query.append(Constant.GROUP_BY).append(model.getGroup());
        }
        if (Objects.nonNull(model.getOrder())) {
            query.append(Constant.ORDER_BY_TIME).append(model.getOrder());
        }
        if (Objects.nonNull(model.getPageNum()) && Objects.nonNull(model.getPageSize())) {
            query.append(Constant.SPACE).append(model.getPageQuery());
        }
        if (model.getUseTimeZone()) {
            query.append(Constant.SPACE).append(model.getTimeZone());
        }
        String sql = query.toString();
        log.warn("The query statement is:{}", sql);
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