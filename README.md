# mobaijun-spring-boot-starter

> 组件化开发，抽取公共模块

```bash
mvn clean deploy -DskipTests -q -B
```
## 快速开始

~~~xml
<!-- spring boot-->
<dependency>
    <groupId>com.mobaijun</groupId>
    <artifactId>mobaijun-spring-boot-dependencies</artifactId>
    <version>${latest.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
~~~

## 版本说明

|  版本号   |           异常模块            |  优化版本  |          说明          |
|:------:|:-------------------------:|:------:|:--------------------:|
| 3.0.19 | nacos-spring-boot-starter | 3.0.20 | nacos 修改打包文件为 jar 格式 |

## 代码包说明
| Module Name | GitHub Link | Description |
| :---: | :---: | :---: |
| base-model-spring-boot-starter | [base-model-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/base-model-spring-boot-starter) | 提供基础数据模型，简化了常用实体类的定义与管理。 |
| bim-spring-boot-starter | [bim-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/bim-spring-boot-starter) | 集成BIM（建筑信息模型），支持BIM数据处理与可视化。 |
| core-spring-boot-starter | [core-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/core-spring-boot-starter) | 核心功能模块，包含通用的工具类和基础配置。 |
| db-helper-spring-boot-starter | [db-helper-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/db-helper-spring-boot-starter) | 数据库操作辅助工具，简化了数据访问层的开发。 |
| dubbo-spring-boot-starter | [dubbo-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/dubbo-spring-boot-starter) | 集成Dubbo分布式服务框架，提供RPC服务的快速配置与使用。 |
| easyexcel-spring-boot-starter | [easyexcel-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/easyexcel-spring-boot-starter) | 集成EasyExcel，简化Excel文件的读写操作。 |
| email-spring-boot-starter | [email-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/email-spring-boot-starter) | 邮件服务集成，支持邮件发送与模板管理。 |
| excel-spring-boot-starter | [excel-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/excel-spring-boot-starter) | 提供Excel操作，支持多种Excel读写场景。 |
| file-spring-boot-starter | [file-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/file-spring-boot-starter) | 文件操作工具，支持文件的上传、下载和管理。 |
| i18n-spring-boot-starter | [i18n-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/i18n-spring-boot-starter) | 提供国际化支持，简化多语言应用的开发。 |
| influxdb-spring-boot-starter | [influxdb-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/influxdb-spring-boot-starter) | 集成InfluxDB时序数据库，支持高效的时序数据存储与查询。 |
| ip2region-spring-boot-starter | [ip2region-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/ip2region-spring-boot-starter) | IP地址定位服务，支持快速查询IP所属区域。 |
| jasypt-spring-boot-starter | [jasypt-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/jasypt-spring-boot-starter) | 提供Jasypt加密支持，增强应用的安全性。 |
| job-spring-boot-starter | [job-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/job-spring-boot-starter) | 定时任务调度模块，支持多种任务调度场景。 |
| jpush-spring-boot-starter | [jpush-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/jpush-spring-boot-starter) | 集成极光推送服务，简化消息推送功能的实现。 |
| json-spring-boot-starter | [json-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/json-spring-boot-starter) | 提供JSON处理工具，支持多种JSON解析与生成场景。 |
| loadbalancer-spring-boot-starter | [loadbalancer-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/loadbalancer-spring-boot-starter) | 负载均衡器，支持多种负载均衡策略。 |
| minio-spring-boot-starter | [minio-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/minio-spring-boot-starter) | 集成MinIO对象存储服务，支持文件的高效存储与管理。 |
| mobaijun-spring-boot-dependencies | [mobaijun-spring-boot-dependencies](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/mobaijun-spring-boot-dependencies) | 全局项目依赖管理，集中管理和简化依赖版本的配置。 |
| mybatis-plus-spring-boot-starter | [mybatis-plus-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/mybatis-plus-spring-boot-starter) | 集成MyBatis-Plus，简化MyBatis的CRUD操作。 |
| nacos-spring-boot-starter | [nacos-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/nacos-spring-boot-starter) | 集成Nacos配置中心与服务发现的，支持动态配置管理与服务注册。 |
| openapi-spring-boot-starter | [openapi-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/openapi-spring-boot-starter) | 提供OpenAPI支持，自动生成API文档，简化接口管理。 |
| operation-log-spring-boot-starter | [operation-log-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/operation-log-spring-boot-starter) | 操作日志记录，支持自动化的操作审计与日志记录。 |
| oss-spring-boot-starter | [oss-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/oss-spring-boot-starter) | 对象存储服务，支持多种云存储服务的无缝集成。 |
| redis-spring-boot-starter | [redis-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/redis-spring-boot-starter) | Redis集成，提供缓存管理与分布式锁等功能。 |
| redisson-spring-boot-starter | [redisson-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/redisson-spring-boot-starter) | 集成Redisson，增强Redis的使用场景和分布式功能。 |
| run-spring-boot-starter | [run-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/run-spring-boot-starter) | 提供应用启动时打开配置好的 https 地址，优化了启动流程和性能。 |
| seata-spring-boot-starter | [seata-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/seata-spring-boot-starter) | 集成Seata分布式事务，支持分布式事务的全局管理。 |
| sensitive-spring-boot-starter | [sensitive-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/sensitive-spring-boot-starter) | 敏感信息保护，支持敏感数据的加密与脱敏。 |
| sms-spring-boot-starter | [sms-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/sms-spring-boot-starter) | 短信服务集成，支持多种短信平台的消息发送。 |
| table-spring-boot-starter | [table-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/table-spring-boot-starter) | 提供表格数据处理，支持复杂表格数据的操作与展示。 |
| test-spring-boot-starter-example | [test-spring-boot-starter-example](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/test-spring-boot-starter-example) | 测试模块示例，提供常见测试场景的解决方案。 |
| web-spring-boot-starter | [web-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/web-spring-boot-starter) | Web应用开发，简化了Web层的配置与开发。 |
| websocket-spring-boot-starter | [websocket-spring-boot-starter](https://github.com/mobaijun/mobaijun-spring-boot-starter/tree/main/websocket-spring-boot-starter) | WebSocket集成，支持实时通信功能的快速实现。 |
