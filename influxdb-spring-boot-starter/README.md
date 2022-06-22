## 简介

这是是个 influxdb 扩展 starter，去除项目繁琐的配置操作

## 快速开始

在项目 pom.xml 引入如下依赖

~~~xml
<dependency>
    <groupId>com.mobaijun</groupId>
    <artifactId>influxdb-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
~~~

## 配置链接信息

~~~yaml
spring:
  # influxdb 配置
  influxdb:
    # 密码
    password: admin
    # 账号
    username: admin
    # 数据库
    database: admin
    # 默认策略
    retention-policy: default
    # 存储日期
    retention-policy-time: 730d
    # 链接地址
    url: http://localhost:8086
~~~

## 启动项目即可

~~~apl
2022-04-27 11:01:13.802  INFO 13100 --- [           main] com.example.demo.DemoApplication         : No active profile set, falling back to default profiles: default
2022-04-27 11:01:14.783  INFO 13100 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8002 (http)
2022-04-27 11:01:14.798  INFO 13100 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-04-27 11:01:14.798  INFO 13100 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.55]
2022-04-27 11:01:14.877  INFO 13100 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-04-27 11:01:14.877  INFO 13100 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1018 ms
2022-04-27 11:01:15.511  INFO 13100 --- [           main] c.m.influxdb.config.InfluxDbConnection   : ============================ Influxdb 创建链接成功 ============================
2022-04-27 11:01:15.618  INFO 13100 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8002 (http) with context path ''
2022-04-27 11:01:15.650  INFO 13100 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 2.428 seconds (JVM running for 5.599)
~~~

调用说明,直接调方法即可。

~~~java
@Resource
private InfluxDbConnection influxDbConnection;
~~~

方法说明

~~~java
public void influxDbBuild(); // 初始化操作
private void createDatabase(String... dbName)；// 创建数据库
public void isEmptyDataBase(String dbName);// 是否有相同数据库
public boolean ping(); // 链接是否正常
public void createRetentionPolicy(String policyName, String duration, int replication, Boolean isDefault)；// 创建自定义策略
public void query(String command);// 查询
public void batchInsert(BatchPoints batchPoints);// 插入
public void insert(BatchPoints points, String database); // 根据数据库插入
public void close();// 关闭链接
public String deleteMeasurementData(String command);// 删除表
~~~

