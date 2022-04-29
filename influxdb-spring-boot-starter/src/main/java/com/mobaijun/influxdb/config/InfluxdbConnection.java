package com.mobaijun.influxdb.config;


import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.core.model.InfluxdbClient;
import com.mobaijun.influxdb.util.InfluxdbUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: influxDbConnection
 * 类描述： influxDb操作类
 * https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=199530389&focusedCommentId=199540198#influxDb%E9%80%82%E9%85%8D%E5%99%A8%E5%85%BC%E5%AE%B9%E8%BF%9B%E5%BA%A6-1.write()
 *
 * @author MoBaiJun 2022/4/27 8:55
 */
public class InfluxdbConnection extends InfluxdbClient {

    private final Logger log = LoggerFactory.getLogger(InfluxdbConnection.class);

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
        if (ObjectUtils.isEmpty(this.influxDb)) {
            this.influxDb = InfluxDBFactory.connect(this.getUrl(), this.getUsername(), this.getPassword(), CLIENT);
            log.info("======================= Influxdb database  Created successfully ============================");
        }
        if (!InfluxdbUtils.checkDatabase(execute(Constant.CREATE_DATABASE), getDatabase())) {
            execute(Constant.SHOW_DATABASE + getDatabase());
        }
    }

    /***
     * 默认执行方法
     * @param query  sql语句
     */
    public QueryResult execute(String query) {
        return influxDb.query(new Query(query, getDatabase()));
    }

    /**
     * 查询 返回对应实体 List
     *
     * @param query sql语句
     * @param clazz 实体
     */
    public <T> List<T> selectList(String query, Class<T> clazz) {
        return InfluxdbUtils.toPojo(execute(query), clazz);
    }

    /**
     * 获取 count
     * 仅支持 Field 字段
     *
     * @param query sql语句
     */
    public long count(String query) {
        return InfluxdbUtils.count(execute(query));
    }

    /**
     * 批量插入
     *
     * @param entity 实体
     */
    public void insert(List<?> entity) {
        List<String> data = new ArrayList<>();
        for (Object object : entity) {
            data.add(InfluxdbUtils.save(object).lineProtocol());
        }
        influxDb.write(data);
    }

    /**
     * 插入
     *
     * @param entity 实体
     */
    public void insert(Object entity) {
        influxDb.write(InfluxdbUtils.save(entity));
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
        if (!ObjectUtils.isEmpty(time)) {
            builder.time(time, TimeUnit.SECONDS);
        }
        influxDb.write(getDatabase(), "", builder.build());
    }

    /**
     * 插入
     * <p>
     * 请使用 Insert 构造
     */
    public void insert(String query) {
        influxDb.write(query);
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
        return InfluxdbUtils.delete(execute(query));
    }

    /**
     * 复杂条件使用 StringBuilder 拼接 sql
     *
     * @param sql 查询sql
     * @return 查询结果
     */
    public List<Object> queryList(StringBuilder sql) {
        QueryResult queryResult = influxDb.query(new Query(String.valueOf(sql), getDatabase()));
        // 对象内容是否正常
        if (ObjectUtils.isEmpty(queryResult) || ObjectUtils.isEmpty(queryResult.getError())) {
            return null;
        }
        // 数据集合是否正常-
        List<QueryResult.Series> series = queryResult.getResults().get(0).getSeries();
        if (series == null) {
            return null;
        }
        List<List<Object>> values = series.get(0).getValues();
        if (ObjectUtils.isEmpty(values) || ObjectUtils.isEmpty(values.size())) {
            return null;
        }
        List<Object> dataList = new LinkedList<>();
        return values.stream().map(v -> dataList.add(v.get(0))).collect(Collectors.toCollection(LinkedList::new));
    }
}
