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
package com.mobaijun.influxdb.util;

import com.mobaijun.influxdb.annotation.Aggregate;
import com.mobaijun.influxdb.annotation.Count;
import com.mobaijun.influxdb.core.Query;
import com.mobaijun.influxdb.core.constant.Constant;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InfluxdbUtils
 * 类描述： influxdb 工具类
 *
 * @author MoBaiJun 2022/4/29 14:29
 */
public class InfluxdbUtils {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(InfluxdbUtils.class);

    /**
     * 将queryResult 转换为实体对象List
     * influxdb-java 本身提供了 InfluxDBResultMapper.toPOJO(queryResult, clazz);
     * 上述方式得用 Instant 时间就很难受
     *
     * @param queryResult queryResult
     * @param clazz       final Class
     * @param <T>         List
     * @return List
     */
    public static <T> List<T> toPojo(final QueryResult queryResult, final Class<T> clazz) {
        Objects.requireNonNull(queryResult, Constant.QUERY_RESULT);
        Objects.requireNonNull(clazz, Constant.CLAZZ);
        List<T> results = new LinkedList<>();
        for (QueryResult.Result result : queryResult.getResults()) {
            if (result.getSeries() != null) {
                for (QueryResult.Series series : result.getSeries()) {
                    List<String> columns = series.getColumns();
                    int fieldSize = columns.size();
                    for (List<Object> value : series.getValues()) {
                        T obj = null;
                        try {
                            obj = clazz.newInstance();
                            for (int i = 0; i < fieldSize; i++) {
                                String fieldName = columns.get(i);
                                Field field = null;
                                // 使用 spring 工具类获取指定字段
                                field = ReflectionUtils.findField(clazz, CommonUtils.lineToHump(fieldName));
                                if (field == null) {
                                    log.error("Field :{} Not fount", fieldName);
                                    continue;
                                }
                                field.setAccessible(true);
                                if (value.get(i) == null) {
                                    continue;
                                }
                                Class<?> type = field.getType();
                                setFieldValue(type, value, i, obj, field);
                            }
                            // tags 仅在group by tag 字段时使用
                            if (series.getTags() != null && !series.getTags().isEmpty()) {
                                for (Map.Entry<String, String> entry : series.getTags().entrySet()) {
                                    Field field = null;
                                    try {
                                        field = clazz.getDeclaredField(CommonUtils.lineToHump(entry.getKey()));
                                        field.setAccessible(true);
                                    } catch (NoSuchFieldException e) {
                                        log.error("Field :{} Not fount, error :{}", entry.getKey(), e.getMessage());
                                    }
                                    if (field != null) {
                                        setFieldValue(obj, field, entry.getValue());
                                    }
                                }
                            }
                        } catch (SecurityException | InstantiationException | IllegalAccessException e) {
                            log.error("Influxdb toPOJO error :{}", e.getMessage());
                        }
                        results.add(obj);
                    }
                }
            } else {
                log.info("QueryResult.Result Is Null.");
            }
        }
        return results;
    }

    /**
     * 获取数据count
     *
     * @param queryResult QueryResult
     * @return long
     */
    public static long count(QueryResult queryResult) {
        for (QueryResult.Result result : queryResult.getResults()) {
            if (result.getSeries() != null) {
                for (QueryResult.Series series : result.getSeries()) {
                    List<String> columns = series.getColumns();
                    int index = columns.indexOf(Constant.CONUNT);
                    if (index != -1) {
                        BigDecimal count = new BigDecimal(series.getValues().get(0).get(index).toString());
                        return count.longValue();
                    }

                }
            }
        }
        return 0;
    }


    /**
     * 保存
     * Point.Builder.field 虽然已过时 理论上不会被删除吧
     * <p>
     * Point.Builder.addField方法不够灵活 如果我是 BigDecimal 就傻了
     *
     * @param object 实体对象
     * @return Point
     */
    public static Point save(Object object) {
        Class<?> clazz = object.getClass();
        Measurement measurement = clazz.getAnnotation(Measurement.class);
        Point.Builder builder = Point.measurement(measurement.name());
        Field[] fields = getAllFields(clazz);
        for (Field field : fields) {
            try {
                Column column = field.getAnnotation(Column.class);
                if (ObjectUtils.isEmpty(column)) {
                    log.error("Influxdb save error. error :{} null", column.name());
                }
                field.setAccessible(true);
                if (column.tag()) {
                    builder.tag(column.name(), field.get(object).toString());
                } else {
                    if (field.get(object) != null) {
                        if (Constant.TIME.equals(column.name())) {
                            builder.time(CommonUtils.parseLocalDateTimeToInstant((LocalDateTime) field.get(object)).getEpochSecond(), TimeUnit.SECONDS);
                        } else {
                            builder.addField(column.name(), field.get(object).toString());
                        }

                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Influxdb save error. error :{}", e.getMessage());
            }
        }
        return builder.build();
    }

    /**
     * 删除数据
     * influxdb-java 本身删除不会返回清理的条数
     * 因此这里的 1 仅为默认成功返回
     * 如果存在错误将抛出具体的错误
     *
     * @param queryResult QueryResult
     * @return long
     */
    public static long delete(QueryResult queryResult) {
        for (QueryResult.Result result : queryResult.getResults()) {
            if (result.getError() != null) {
                throw new RuntimeException(result.getError());
            }
        }
        return 1;
    }

    /**
     * 检测数据库是否已存在
     *
     * @param queryResult  执行结果
     * @param databaseName yml默认配置中的数据库名
     * @return boolean
     */
    public static boolean checkDatabase(QueryResult queryResult, String databaseName) {
        for (QueryResult.Result result : queryResult.getResults()) {
            if (result.getSeries() != null) {
                for (QueryResult.Series series : result.getSeries()) {
                    for (List<Object> databases : series.getValues()) {
                        if (databaseName.equals(databases.get(0))) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    /**
     * 赋值
     *
     * @param type  type
     * @param value value
     * @param i     i
     * @param obj   obj
     * @param field field
     * @throws IllegalAccessException IllegalAccessException
     */
    private static void setFieldValue(Class<?> type, List<Object> value, int i, Object obj, Field field) throws IllegalAccessException {
        if (type.equals(Long.class)) {
            BigDecimal v = new BigDecimal(value.get(i).toString());
            field.set(obj, v.longValue());
        } else if (type.equals(Integer.class)) {
            BigDecimal v = new BigDecimal(value.get(i).toString());
            field.set(obj, v.intValue());
        } else if (type.equals(Float.class)) {
            BigDecimal v = new BigDecimal(value.get(i).toString());
            field.set(obj, v.floatValue());
        } else if (type.equals(Double.class)) {
            BigDecimal v = new BigDecimal(value.get(i).toString());
            field.set(obj, v.doubleValue());
        } else if (type.equals(BigDecimal.class)) {
            BigDecimal v = new BigDecimal(value.get(i).toString());
            field.set(obj, v);
        } else if (type.equals(LocalDateTime.class)) {
            field.set(obj, CommonUtils.parseStringToLocalDateTime(value.get(i).toString()));
        } else {
            field.set(obj, value.get(i));
        }
    }

    /**
     * 赋值
     *
     * @param obj   obj
     * @param field field
     * @param value value
     * @throws IllegalAccessException IllegalAccessException
     */
    private static void setFieldValue(Object obj, Field field, Object value) throws IllegalAccessException {
        field.set(obj, value);
    }

    /**
     * 获取表名
     *
     * @param clazz Class
     * @param <T>   String
     * @return String
     */
    public static <T> String getMeasurement(Class<T> clazz) {
        Measurement measurement = clazz.getAnnotation(Measurement.class);
        return measurement.name();
    }

    /**
     * 获取count注解value 作为count查询
     *
     * @param clazz Class
     * @param <T>   String
     * @return String
     */
    public static <T> String getCountField(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Count count = field.getAnnotation(Count.class);
            if (!ObjectUtils.isEmpty(count)) {
                return count.value();
            }
        }
        throw new RuntimeException("请使用@Count注解到相应的字段上");
    }

    /**
     * 聚合函数查询拼接select条件
     *
     * @param clazz Class
     * @param <T>   String
     * @return String
     */
    public static <T> String getAggregateSelect(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder temp = new StringBuilder();
        int i = 0;
        for (Field field : fields) {
            Aggregate aggregate = field.getAnnotation(Aggregate.class);
            if (!ObjectUtils.isEmpty(aggregate)) {
                if (i != 0) {
                    temp.append(Constant.COMMA);
                }
                temp.append(Query.funcAggregate(aggregate.tag().getTag(), aggregate.value()));
                i++;
            }
        }
        return temp.toString();
    }

    /**
     * 通过反射获取父类属性
     *
     * @param clazz 对象
     * @return 属性数组
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (Object.class != clazz) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        return fieldList.toArray(new Field[0]);
    }
}