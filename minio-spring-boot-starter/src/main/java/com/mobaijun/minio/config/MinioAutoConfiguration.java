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
package com.mobaijun.minio.config;

import com.mobaijun.minio.prop.MinioConfigurationProperties;
import com.mobaijun.minio.util.MinioService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioAutoConfiguration
 * class description：minio configuration class
 *
 * @author MoBaiJun 2022/9/19 17:28
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
public class MinioAutoConfiguration {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(MinioAutoConfiguration.class);

    /**
     * 创建 Minio 配置属性 Bean
     *
     * @return MinioConfigurationProperties 实例
     */
    @Bean
    public MinioConfigurationProperties minioConfigurationProperties() {
        return new MinioConfigurationProperties();
    }

    /**
     * 创建 Minio 服务 Bean
     *
     * @param minioClient                  MinioClient 实例
     * @param minioConfigurationProperties Minio 配置属性
     * @return MinioService 实例
     */
    @Bean
    public MinioService minioService(MinioClient minioClient, MinioConfigurationProperties minioConfigurationProperties) {
        return new MinioService(minioClient, minioConfigurationProperties);
    }

    /**
     * 创建 Minio 客户端 Bean
     *
     * @return MinioClient 实例
     * @throws Exception 如果创建客户端或检查/创建桶时发生错误
     */
    @Bean
    public MinioClient minioClient() throws Exception {
        MinioConfigurationProperties properties = minioConfigurationProperties();
        MinioClient minioClient = createMinioClient(properties);

        // 检查桶是否存在，并根据配置创建桶
        try {
            checkAndCreateBucket(minioClient, properties);
        } catch (Exception e) {
            log.error("Error occurred while checking or creating the bucket: {}", e.getMessage(), e);
            throw new MinioException("Error occurred while checking or creating the bucket: " + e.getMessage());
        }
        return minioClient;
    }

    /**
     * 根据配置创建 Minio 客户端
     *
     * @param properties Minio 配置属性
     * @return MinioClient 实例
     */
    private MinioClient createMinioClient(MinioConfigurationProperties properties) {
        MinioClient.Builder builder = MinioClient.builder()
                .endpoint(properties.getUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey());

        if (configuredProxy()) {
            builder.httpClient(client());
        }
        MinioClient minioClient = builder.build();
        minioClient.setTimeout(
                properties.getConnectTimeout().toMillis(),
                properties.getWriteTimeout().toMillis(),
                properties.getReadTimeout().toMillis()
        );
        return minioClient;
    }

    /**
     * 检查桶是否存在。如果不存在，根据配置创建桶
     *
     * @param minioClient MinioClient 实例
     * @param properties  Minio 配置属性
     * @throws MinioException           如果与 MinIO 通信时发生错误
     * @throws IOException              如果 IO 操作失败
     * @throws NoSuchAlgorithmException 如果没有该算法
     * @throws InvalidKeyException      如果密钥无效
     */
    private void checkAndCreateBucket(MinioClient minioClient, MinioConfigurationProperties properties) throws MinioException,
            IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (properties.isCheckBucket()) {
            log.debug("Checking if bucket {} exists", properties.getBucket());

            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(properties.getBucket())
                    .build());

            if (!bucketExists) {
                if (properties.isCreateBucket()) {
                    createBucket(minioClient, properties.getBucket());
                } else {
                    throw new IllegalStateException("Bucket does not exist and bucket creation is disabled: " + properties.getBucket());
                }
            }
        }
    }

    /**
     * 创建桶
     *
     * @param minioClient MinioClient 实例
     * @param bucketName  桶的名称
     * @throws MinioException 如果创建桶时发生错误
     */
    private void createBucket(MinioClient minioClient, String bucketName) throws MinioException {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            log.info("Bucket '{}' created successfully", bucketName);
        } catch (Exception e) {
            log.error("Error occurred while creating bucket '{}': {}", bucketName, e.getMessage(), e);
            throw new MinioException("Error occurred while creating bucket: " + e.getMessage());
        }
    }

    /**
     * 检查是否配置了代理
     *
     * @return 如果配置了代理，返回 true；否则返回 false
     */
    private boolean configuredProxy() {
        String httpHost = System.getProperty("http.proxyHost");
        String httpPort = System.getProperty("http.proxyPort");
        return StringUtils.hasText(httpHost) && StringUtils.hasText(httpPort);
    }

    /**
     * 配置客户端代理
     *
     * @return 配置了代理的 OkHttpClient 实例
     */
    private OkHttpClient client() {
        String httpHost = System.getProperty("http.proxyHost");
        String httpPort = System.getProperty("http.proxyPort");

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (StringUtils.hasText(httpHost)) {
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpHost, Integer.parseInt(httpPort))));
        }
        return builder.build();
    }
}