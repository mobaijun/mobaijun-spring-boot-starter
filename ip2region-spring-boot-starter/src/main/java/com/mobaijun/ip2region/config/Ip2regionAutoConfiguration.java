/*
 * Copyright (C) 2022 [www.mobaijun.com]
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
package com.mobaijun.ip2region.config;

import com.mobaijun.ip2region.enums.CacheType;
import com.mobaijun.ip2region.properties.Ip2regionProperties;
import com.mobaijun.ip2region.searcher.CacheVectorIndexIp2regionSearcher;
import com.mobaijun.ip2region.searcher.CacheXdbFileIp2regionSearcher;
import com.mobaijun.ip2region.searcher.Ip2regionSearcher;
import com.mobaijun.ip2region.searcher.NoneCacheIp2regionSearcher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

/**
 * Description: [自动配置类]
 * Author: [mobaijun]
 * Date: [2024/8/15 10:19]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Configuration
@EnableConfigurationProperties(Ip2regionProperties.class)
public class Ip2regionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Ip2regionSearcher ip2regionSearcher(ResourceLoader resourceLoader, Ip2regionProperties properties) {
        CacheType cacheType = properties.getCacheType();
        return switch (cacheType) {
            case XDB -> new CacheXdbFileIp2regionSearcher(resourceLoader, properties);
            case NONE -> new NoneCacheIp2regionSearcher(resourceLoader, properties);
            case VECTOR_INDEX -> new CacheVectorIndexIp2regionSearcher(resourceLoader, properties);
        };
    }
}
