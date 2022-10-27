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
import com.mobaijun.influxdb.core.model.DeleteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Delete
 * 类描述： 删除
 *
 * @author MoBaiJun 2022/4/29 14:04
 */
public class Delete extends BaseQuery {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(Delete.class);

    /**
     * 构造条件
     * 注意 where 条件中 map参数仅能是 tag
     * 这是由 influxdb 本身决定的
     *
     * @param model DeleteModel
     * @return String
     */
    public static String build(DeleteModel model) {
        Objects.requireNonNull(model.getMeasurement(), Constant.DELETE_MEASUREMENT);
        StringBuilder delete = new StringBuilder();
        delete.append(Constant.DELETE_DROM).append(model.getMeasurement()).append(Constant.DELIMITER);
        if (!ObjectUtils.isEmpty(model.getWhere())) {
            delete.append(Constant.WHERE).append(model.getWhere());
        } else {
            throw new RuntimeException("where 条件缺失");
        }
        String sql = delete.toString();
        log.warn("The delete statement is:{}", sql);
        return sql;
    }
}