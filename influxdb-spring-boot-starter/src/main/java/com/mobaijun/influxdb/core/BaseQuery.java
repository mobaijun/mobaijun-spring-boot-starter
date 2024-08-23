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
import com.mobaijun.influxdb.core.model.BaseModel;
import com.mobaijun.influxdb.util.CommonUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: BaseQuery
 * 类描述：查询基类
 *
 * @author MoBaiJun 2022/4/29 14:15
 */
public abstract class BaseQuery {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(BaseQuery.class);

    /**
     * 开始时间结束时间
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 开始时间结束时间
     */
    public static StringBuilder time(LocalDateTime start, LocalDateTime end) {
        Instant startTime = CommonUtil.parseLocalDateTimeToInstant(start);
        Instant endTime = CommonUtil.parseLocalDateTimeToInstant(end);
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.TIME_AND).append(startTime);
        sb.append(Constant.AND_TIME).append(endTime).append(Constant.DELIMITER);
        return sb;
    }

    /**
     * 默认条件 为 =
     * 其他条件暂时未实现请自行构造
     *
     * @param model 模块
     * @return String
     */
    public static String where(BaseModel model) {
        StringBuilder sb = new StringBuilder();
        if (!Objects.isNull(model.getStart()) && !Objects.isNull(model.getEnd())) {
            sb = time(model.getStart(), model.getEnd());
            if (!Objects.isNull(model.getMap())) {
                for (Map.Entry<String, Object> entry : model.getMap().entrySet()) {
                    sb.append(Constant.AND).append(Constant.DELIMITER).append(entry.getKey()).append("\"").append("=");
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        sb.append(Constant.DELIMITER).append(value).append(Constant.DELIMITER);
                    } else {
                        sb.append(entry.getValue());
                    }
                }
            }
        } else {
            if (!Objects.isNull(model.getMap())) {
                int i = 0;
                for (Map.Entry<String, Object> entry : model.getMap().entrySet()) {
                    if (i != 0) {
                        sb.append(Constant.AND);
                    }
                    sb.append(Constant.DELIMITER).append(entry.getKey()).append(Constant.DELIMITER).append(Constant.EQUAL_SIGN);
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        sb.append(Constant.DELIMITER).append(value).append(Constant.DELIMITER);
                    } else {
                        sb.append(entry.getValue());
                    }
                    i++;
                }
            }
        }
        log.warn("The basic query statement is : {}", sb);
        return sb.toString();
    }
}