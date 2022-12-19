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
import com.mobaijun.influxdb.util.CommonUtils;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: Insert
 * 类描述： 新增
 *
 * @author MoBaiJun 2022/4/29 14:03
 */
public class Insert extends BaseQuery {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(Insert.class);

    /**
     * 构造条件
     *
     * @param object Object
     * @return String
     */
    public static String build(Object object) {
        Objects.requireNonNull(object, "实体不能为空");
        StringBuilder insert = new StringBuilder();
        String time = "";
        Class<?> clazz = object.getClass();
        Measurement measurement = clazz.getAnnotation(Measurement.class);
        insert.append(measurement.name());
        Field[] fields = clazz.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            try {
                Column column = field.getAnnotation(Column.class);
                field.setAccessible(true);
                if (column.tag()) {
                    if (field.get(object) != null) {
                        insert.append(Constant.COMMA).append(column.name()).append(Constant.EQUAL_SIGN).append(field.get(object));
                    }
                } else {
                    if (Objects.nonNull(field.get(object))) {
                        if (Constant.TIME.equals(column.name())) {
                            time = CommonUtils.parseLocalDateTimeToInstant((LocalDateTime) field.get(object)).getEpochSecond() + "000000000";
                        } else {
                            if (i == 0) {
                                insert.append(Constant.SPACE);
                            } else {
                                insert.append(Constant.COMMA);
                            }
                            insert.append(column.name()).append(Constant.EQUAL_SIGN).append(field.get(object));
                            i++;
                        }

                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Influxdb save error. error :{}", e.getMessage());
            }
        }
        insert.append(Constant.SPACE).append(time);
        String sql = insert.toString();
        log.warn("The new sentence is :{}" + sql);
        return sql;
    }
}