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
package com.mobaijun.influxdb.config;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.prop.InfluxProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InfluxdbConfiguration
 * 类描述： influxdb配置
 *
 * @author MoBaiJun 2022/4/27 8:51
 */
@Configuration
@EnableConfigurationProperties(value = {InfluxProperties.class})
@ConditionalOnProperty(prefix = InfluxProperties.PREFIX, value = Constant.ENABLE, matchIfMissing = true)
public class InfluxdbAutoConfiguration {

    public InfluxdbAutoConfiguration() {
    }

    @Bean
    public InfluxProperties influxDbProperties() {
        return new InfluxProperties();
    }

    /**
     * 初始化influxdb
     *
     * @param influxProp 初始化属性
     * @return 配置属性
     */
    @Bean
    @ConditionalOnMissingBean(InfluxdbConnection.class)
    public InfluxdbConnection getInfluxdbConnection(@Qualifier("influxDbProperties") InfluxProperties influxProp) {
        return new InfluxdbConnection(
                influxProp.getUsername(),
                influxProp.getPassword(),
                influxProp.getUrl(),
                influxProp.getDatabase(),
                influxProp.getRetentionPolicy(),
                influxProp.getRetentionPolicyTime());
    }
}