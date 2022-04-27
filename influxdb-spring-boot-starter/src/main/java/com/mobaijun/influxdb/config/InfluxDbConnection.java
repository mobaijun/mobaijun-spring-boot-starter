package com.mobaijun.influxdb.config;

import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.InfluxDBException;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InfluxDbConnection
 * 类描述： influxdb操作类
 * https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=199530389&focusedCommentId=199540198#InfluxDB%E9%80%82%E9%85%8D%E5%99%A8%E5%85%BC%E5%AE%B9%E8%BF%9B%E5%BA%A6-1.write()
 *
 * @author MoBaiJun 2022/4/27 8:55
 */
public class InfluxDbConnection {

    private final Logger log = LoggerFactory.getLogger(InfluxDbConnection.class);

    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * 连接地址
     */
    private final String url;

    /**
     * 数据库
     */
    private final String database;

    /**
     * 数据保留策略
     */
    private final String retentionPolicy;

    /**
     * influxdb 默认接口
     */
    private InfluxDB influxDb;

    /**
     * 保存时间
     */
    private final String retentionPolicyTime;

    /**
     * 防止查询超时
     */
    private static final OkHttpClient.Builder CLIENT = new OkHttpClient.Builder().readTimeout(100, TimeUnit.SECONDS);

    /**
     * 策略默认值
     */
    private static final String DEFAULT = "default";

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
    public InfluxDbConnection(String username, String password, String url, String database, String retentionPolicy, String retentionPolicyTime) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.database = database;
        this.retentionPolicy = retentionPolicy == null || "".equals(retentionPolicy) ? DEFAULT : retentionPolicy;
        this.retentionPolicyTime = retentionPolicyTime;
        influxDbBuild();
    }

    /**
     * 链接influxdb ，若不存在则创建
     */
    public void influxDbBuild() {
        if (influxDb == null) {
            // 初始化链接
            influxDb = InfluxDBFactory.connect(url, username, password, CLIENT);
            log.info("============================ Influxdb 创建链接成功 ============================");
        }
        // 创建数据库
        try {
            createDatabase(database);
            influxDb.setDatabase(database);
        } catch (NullPointerException e) {
            throw new NullPointerException("create influx database failed, error{}");
        } finally {
            // 默认策略
            if (DEFAULT.equalsIgnoreCase(retentionPolicy)) {
                createDefaultRetentionPolicy();
                influxDb.setRetentionPolicy(retentionPolicy);
            }
        }
        influxDb.setLogLevel(LogLevel.FULL);
    }

    /**
     * 创建数据库
     *
     * @param dbName 数据库名称
     */
    private void createDatabase(String... dbName) {
        if (dbName.length > 0) {
            influxDb.createDatabase(dbName[0]);
            return;
        }
        isEmptyDataBase(database);
    }

    /**
     * 判断是否存在数据库
     *
     * @param dbName 数据库名称
     */
    public void isEmptyDataBase(String dbName) {
        if (StringUtils.hasLength(dbName)) {
            log.info("如参数不指定数据库名称，配置文件 spring.influx.database 必须指定");
            influxDb.createDatabase(database);
        }
        // 判断是否存在数据库
        if (influxDb.databaseExists(dbName)) {
            throw new NullPointerException(dbName);
        }
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
            if (!ObjectUtils.isEmpty(ping)) {
                isConnected = true;
            }
            // 链接超时
        } catch (Exception e) {
            log.error("influxdb Pong -------------- 无法链接");
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * 创建自定义保留策略
     *
     * @param policyName  策略名
     * @param duration    保存天数
     * @param replication 保存副本数量
     * @param isDefault   是否设为默认保留策略
     */
    public void createRetentionPolicy(String policyName, String duration, int replication, Boolean isDefault) {
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s ", policyName, database, duration, replication);
        if (isDefault) {
            sql = sql + " DEFAULT";
        }
        this.query(sql);
    }

    /**
     * 创建默认的保留策略
     * 策略名：default，保存天数：730天，保存副本数量：1
     * 设为默认保留策略
     */
    public void createDefaultRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT", retentionPolicy, database, retentionPolicyTime, 1);
        this.query(command);
    }

    /**
     * 查询
     *
     * @param command 查询语句
     */
    public void query(String command) {
        influxDb.query(new Query(command, database));
    }

    /**
     * 写入数据
     *
     * @param batchPoints 表结构
     */
    public void batchInsert(BatchPoints batchPoints) {
        influxDb.write(batchPoints);
    }


    /**
     * 批量写入influxdb，添加数据库判断
     *
     * @param points   表结构
     * @param database 数据库
     */
    public void insert(BatchPoints points, String database) {
        // 批量写入influxDB
        if (points.getPoints().size() > 0) {
            if (!influxDb.databaseExists(database)) {
                createDatabase(database);
            }
            try {
                influxDb.write(points);
            } catch (InfluxDBException e) {
                influxDbBuild();
            }
        }
    }

    /**
     * 关闭链接
     */
    public void close() {
        influxDb.close();
    }

    /**
     * 删除
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDb.query(new Query(command, database));
        return result.getError();
    }
}
