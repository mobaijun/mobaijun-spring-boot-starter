# minio 工具类

### 快速开始

1. 导入依赖

~~~xml

<dependency>
    <!-- https://central.sonatype.com//artifact/com.mobaijun/minio-spring-boot-starter -->
    <groupId>com.mobaijun</groupId>
    <artifactId>minio-spring-boot-starter</artifactId>
    <version>${project.version}</version>
</dependency>
~~~

2. yml 配置

~~~yaml
spring:
  # 配置minio 文件上传
  minio:
    # 文件上传的域名
    url: http://192.168.110.201:35152
    # 文件上传的accessKey
    access-key: sgrwylStGau00HXqWDwY
    # 文件上传的secretKey
    secret-key: dPFXxtzAh1vN2PY5tEoHSJrNcd31EgVJ10mAdKA8
    # 文件上传的bucket
    bucket: test
~~~

3. 注入配置 bean，上传文件

~~~java
/**
 * 文件上传服务
 */
private final MinioService minioService;

@Override
public String uploadFile(MultipartFile file) {
    try {
        // 几行代码完成执行上传功能
        return minioService.interceptUrl(file.getInputStream(), Path.of(file.getOriginalFilename()), file.getContentType());
    } catch (IOException | MinioException e) {
        // 如果出现 IO 或 MinIO 相关异常，抛出运行时异常
        throw new RuntimeException(e);
    }
}
~~~

