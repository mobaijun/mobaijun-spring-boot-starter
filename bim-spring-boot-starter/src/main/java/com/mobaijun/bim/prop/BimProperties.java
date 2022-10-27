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
package com.mobaijun.bim.prop;

import com.bimface.sdk.config.Config;
import com.bimface.sdk.config.Endpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BimProperties
 * class description： bim 配置文件
 *
 * @author MoBaiJun 2022/10/10 9:59
 */
@ConfigurationProperties(BimProperties.PREFIX)
@EnableConfigurationProperties(BimProperties.class)
public class BimProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "bim.config";

    /**
     * 私钥
     */
    private String appKey;


    /**
     * 密钥
     */
    private String appSecret;

    /**
     * bim 配置信息
     */
    private Config config;

    /**
     * API调用地址入口配置
     */
    private Endpoint endpoint;

    @Override
    public String toString() {
        return "BimProperties{" +
                "appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", config=" + config +
                ", endpoint=" + endpoint +
                '}';
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
}