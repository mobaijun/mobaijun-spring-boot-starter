这是一个 swagger 扩展 starter ，去除项目繁琐的配置操作

~~~xml
<dependency>
    <groupId>com.mobaijun</groupId>
    <artifactId>swagger-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
~~~

* 配置项目信息

~~~yaml
server:
  port: 8002

swagger:
  # 开启swagger
  enable: true
  # 标题
  title: spring-boot-swagger-demo
  # 服务地址
  host: localhost:${server.port}
  # 版本
  version: 1.0.0
  # 组名称
  group-name: 研发部
  # 描述
  description: 这是一个demo
  # 程序地址
  terms-of-service-url: https://localhost:${server.port}/index.html
  # 作者信息配置
  contact:
    # 作者信息
    author: mobaijun
    # 博客地址或官网地址
    url: https://www.mobaijun.com
    # 配置邮箱
    email: mobaijun8@163.com
~~~

* 开启 swagger

~~~java
package com.example.demo;

import com.mobaijun.swagger.annotation.EnableSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author mobaijun url:https://www.mobaijun.com
 * @EnableSwagger 开启 swagger
 */
@Slf4j
@SpringBootApplication
@EnableSwagger
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        Environment environment = context.getBean(Environment.class);
        log.info("http://localhost:{}", environment.getProperty("server.port") + "/index.html");
        log.info("http://localhost:{}", environment.getProperty("server.port") + "/doc.html");
        log.info("http://localhost:{}", environment.getProperty("server.port") + "/swagger-ui/index.html");
    }
}
~~~

启动项目即可，构建成功信息如下

~~~apl
2022-04-26 13:48:34.115  INFO 8764 --- [           main] com.example.demo.DemoApplication         : No active profile set, falling back to default profiles: default
2022-04-26 13:48:35.108  INFO 8764 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8002 (http)
2022-04-26 13:48:35.136  INFO 8764 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-04-26 13:48:35.136  INFO 8764 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.41]
2022-04-26 13:48:35.221  INFO 8764 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-04-26 13:48:35.221  INFO 8764 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1055 ms
2022-04-26 13:48:35.738  INFO 8764 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2022-04-26 13:48:35.879  INFO 8764 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8002 (http) with context path ''
2022-04-26 13:48:35.914  INFO 8764 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 2.4 seconds (JVM running for 5.539)
2022-04-26 13:48:35.916  INFO 8764 --- [           main] com.example.demo.DemoApplication         : http://localhost:8002/index.html
2022-04-26 13:48:35.917  INFO 8764 --- [           main] com.example.demo.DemoApplication         : http://localhost:8002/doc.html
2022-04-26 13:48:35.917  INFO 8764 --- [           main] com.example.demo.DemoApplication         : http://localhost:8002/swagger-ui/index.html
~~~

页面效果：

![Version-2.2.0.RELEASE](https://s2.loli.net/2022/04/26/DvmJSiKlaV5bHW8.png)