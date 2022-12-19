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
package com.mobaijun.influxdb.config;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.core.model.AbstractInfluxdbClient;
import com.mobaijun.influxdb.util.InfluxdbUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: influxDbConnection
 * 类描述： influxDb操作类
 * <a href="https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=199530389&focusedCommentId=199540198#influxDb%E9%80%82%E9%85%8D%E5%99%A8%E5%85%BC%E5%AE%B9%E8%BF%9B%E5%BA%A6-1.write">...</a>
 *
 * @author MoBaiJun 2022/4/27 8:55
 */
public class InfluxdbConnection extends AbstractInfluxdbClient {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(InfluxdbConnection.class);

    /**
     * influxDb 默认接口
     */
    private InfluxDB influxDb;

    /**
     * 初始化构造方法
     *
     * @param username            用户名
     * @param password            密码
     * @param url                 链接地址
     * @param database            数据库
     * @param retentionPolicy     存储策略
     * @param retentionPolicyTime 存储时间
     */
    public InfluxdbConnection(String username, String password, String url, String database, String retentionPolicy, String retentionPolicyTime) {
        super(username, password, url, database, retentionPolicy, retentionPolicyTime);
        initDefaultDatabase();
    }

    /**
     * 初始化数据库
     */
    @PostConstruct
    private void initDefaultDatabase() {
        if (Objects.isNull(this.influxDb)) {
            this.influxDb = InfluxDBFactory.connect(this.getUrl(), this.getUsername(), this.getPassword(), CLIENT);
            log.info("=================== The influxdb database was initialized successfully!  ===================");
        }
        if (!InfluxdbUtils.checkDatabase(execute(Constant.SHOW_DATABASE), getDatabase())) {
            execute(Constant.CREATE_DATABASE + getDatabase() + Constant.DELIMITER);
        }
    }

    /***
     * 默认执行方法
     * @param query  sql语句
     * @return QueryResult
     */
    public QueryResult execute(String query) {
        log.info("The query SQL statement is: " + query);
        return influxDb.query(new Query(query, getDatabase()));
    }

    /**
     * 查询 返回对应实体 List
     *
     * @param query sql语句
     * @param <T>   List
     * @param clazz 实体
     * @return 返回对应实体List
     */
    public <T> List<T> selectList(String query, Class<T> clazz) {
        log.debug("The query SQL statement is: " + query);
        return InfluxdbUtils.toPojo(execute(query), clazz);
    }

    /**
     * 获取 count
     * 仅支持 Field 字段
     *
     * @param query sql语句
     * @return long
     */
    public long count(String query) {
        log.debug("The query SQL statement is: " + query);
        return InfluxdbUtils.count(execute(query));
    }

    /**
     * 批量插入
     *
     * @param data 数据列表
     */
    public <T> void insert(List<T> data) {
        BatchPoints batchPoints = BatchPoints.database(getDatabase()).build();
        data.forEach(item -> batchPoints.point(InfluxdbUtils.save(item)));
        // 批量写入
        influxDb.write(batchPoints);
    }

    /**
     * 插入
     *
     * @param data 实体
     */
    public <T> void insert(T data) {
        influxDb.write(BatchPoints.database(getDatabase()).build().point(InfluxdbUtils.save(data)));
    }

    /**
     * 插入
     *
     * @param tags        标签索引字段map
     * @param fields      普通字段map
     * @param time        时间可选 可设置为null 即自动生成
     * @param measurement 表
     */
    public void insert(Map<String, String> tags, Map<String, Object> fields, Long time, String measurement) {
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        if (Objects.nonNull(time)) {
            builder.time(time, TimeUnit.SECONDS);
        }
        influxDb.write(BatchPoints.database(getDatabase()).build().point(InfluxdbUtils.save(builder)));
    }

    /**
     * 插入
     * 请使用 Insert 构造
     *
     * @param query 查询语句
     */
    public void insert(String query) {
        influxDb.write(BatchPoints.database(getDatabase()).build().point(InfluxdbUtils.save(query)));
    }

    /**
     * 删除
     * 只允许根据tag和时间来进行删除操作
     * field字段删除无效
     * 不推荐使用
     *
     * @param query sql语句
     */
    public long delete(String query) {
        log.debug("The query SQL statement is: " + query);
        return InfluxdbUtils.delete(execute(query));
    }

    /**
     * 复杂条件使用 StringBuilder 拼接 sql
     *
     * @param sql 查询sql
     * @return 查询结果
     */
    public List<List<Object>> queryList(StringBuilder sql) {
        QueryResult queryResult = influxDb.query(new Query(String.valueOf(sql), getDatabase()));
        log.debug("The query SQL statement is: " + sql);
        // 对象内容是否正常
        if (Objects.isNull(queryResult) || !Objects.isNull(queryResult.getError())) {
            return null;
        }
        // 数据集合是否正常-
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        if (Objects.isNull(series)) {
            return null;
        }
        List<List<Object>> values = series.get(0).getValues();
        if (Objects.isNull(values) || values.size() == 0) {
            return null;
        }
        return values;
    }

    /**
     * 查询统计数据, 查询结果为单行
     *
     * @param sql 执行sql
     * @return 结果集
     */
    public Map<String, Object> queryStatisticData(StringBuilder sql) {
        QueryResult queryResult = influxDb.query(new Query(String.valueOf(sql), getDatabase()));
        // 对象内容是否正常
        if (ObjectUtils.isEmpty(queryResult) || !ObjectUtils.isEmpty(queryResult.getError())) {
            return null;
        }
        // 数据集合是否正常-
        List<List<Object>> values = queryResult.getResults().get(0).getSeries().get(0).getValues();
        if (ObjectUtils.isEmpty(values) || ObjectUtils.isEmpty(values.size())) {
            return null;
        }

        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        HashMap<String, Object> resultMap = new HashMap<>(1);
        for (int index = 0; index < series.get(0).getColumns().size(); index++) {
            resultMap.put(series.get(0).getColumns().get(index), values.get(0).get(index));
        }
        return resultMap;
    }

    /**
     * 关闭链接
     */
    public void close() {
        influxDb.close();
    }

    /**
     * 删除表
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        return influxDb.query(new Query(command, getDatabase())).getError();
    }

    /**
     * 测试链接是否正常
     *
     * @return true 正常 : false 异常
     */
    public boolean ping() {
        boolean isConnected = false;
        try {
            Pong ping = influxDb.ping();
            if (Objects.nonNull(ping)) {
                isConnected = true;
            }
            // 链接超时
        } catch (Exception e) {
            log.error("influxdb Pong -------------- 无法链接");
            e.printStackTrace();
        }
        return isConnected;
    }
}