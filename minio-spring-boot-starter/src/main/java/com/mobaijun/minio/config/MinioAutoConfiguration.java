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
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MinioAutoConfiguration
 * class description： minio configuration class
 *
 * @author MoBaiJun 2022/9/19 17:28
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
public class MinioAutoConfiguration {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(MinioAutoConfiguration.class);

    @Bean
    public MinioConfigurationProperties minioConfigurationProperties() {
        return new MinioConfigurationProperties();
    }

    @Bean
    public MinioService minioService(MinioClient minioClient, MinioConfigurationProperties minioConfigurationProperties) {
        return new MinioService(minioClient, minioConfigurationProperties);
    }

    @Bean
    public MinioClient minioClient() throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        MinioConfigurationProperties minioConfigurationProperties = minioConfigurationProperties();
        MinioClient minioClient;
        // proxy configuration
        if (!configuredProxy()) {
            minioClient = MinioClient.builder()
                    .endpoint(minioConfigurationProperties.getUrl())
                    .credentials(minioConfigurationProperties.getAccessKey(), minioConfigurationProperties.getSecretKey())
                    .build();
        } else {
            minioClient = MinioClient.builder()
                    .endpoint(minioConfigurationProperties.getUrl())
                    .credentials(minioConfigurationProperties.getAccessKey(), minioConfigurationProperties.getSecretKey())
                    .httpClient(client())
                    .build();
        }
        minioClient.setTimeout(
                minioConfigurationProperties.getConnectTimeout().toMillis(),
                minioConfigurationProperties.getWriteTimeout().toMillis(),
                minioConfigurationProperties.getReadTimeout().toMillis()
        );

        // check barrel
        if (minioConfigurationProperties.isCheckBucket()) {
            try {
                log.debug("Checking if bucket {} exists", minioConfigurationProperties.getBucket());
                BucketExistsArgs existsArgs = BucketExistsArgs.builder()
                        .bucket(minioConfigurationProperties.getBucket())
                        .build();
                boolean b = minioClient.bucketExists(existsArgs);
                if (!b) {
                    if (minioConfigurationProperties.isCreateBucket()) {
                        try {
                            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                                    .bucket(minioConfigurationProperties.getBucket())
                                    .build();
                            minioClient.makeBucket(makeBucketArgs);
                        } catch (Exception e) {
                            throw new MinioException("Cannot create bucket", e.getMessage());
                        }
                    } else {
                        throw new IllegalStateException("Bucket does not exist: " + minioConfigurationProperties.getBucket());
                    }
                }
            } catch (Exception e) {
                log.error("Error while checking bucket", e);
                throw e;
            }
        }

        return minioClient;
    }

    /**
     * Whether to enable proxy configuration
     *
     * @return true 是 | false 否
     */
    private boolean configuredProxy() {
        String httpHost = System.getProperty("http.proxyHost");
        String httpPort = System.getProperty("http.proxyPort");
        return StringUtils.hasText(httpPort) && StringUtils.hasText(httpPort);
    }

    /**
     * Client proxy configuration
     *
     * @return OkHttpClient
     */
    private OkHttpClient client() {
        String httpHost = System.getProperty("http.proxyHost");
        String httpPort = System.getProperty("http.proxyPort");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (StringUtils.hasText(httpHost)) {
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpHost, Integer.parseInt(httpPort))));
        }
        return builder
                .build();
    }
}