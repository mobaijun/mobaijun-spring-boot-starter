# table-spring-boot-starter

> 本项目是为了方便自动导入一些通用的配置表，（字典，项目配置表，全国地图信息等）初始化数据地址：
>
> <a href="https://github.com/april-projects/init-data/">...</a>

## 使用教程

* 引入最新版本

~~~xml
<dependency>
    <groupId>com.mobaijun</groupId>
    <artifactId>table-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
~~~

* 配置如下参数

~~~yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test
    password: 123456
    username: user
    # 自动生成表配置
    table-config:
      # 表前缀
      table-prefix: kjs_
      # 全部表
      type-enum: all
~~~

> 查看表内容，已经自动生成相关数据表了
