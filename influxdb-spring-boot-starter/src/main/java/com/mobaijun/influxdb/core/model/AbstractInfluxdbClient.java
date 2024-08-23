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
package com.mobaijun.influxdb.core.model;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: InfluxdbClient
 * 类描述： influxdb 客户端 model
 *
 * @author MoBaiJun 2022/4/29 14:05
 */
public abstract class AbstractInfluxdbClient {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接地址
     */
    private String url;

    /**
     * 数据库
     */
    private String database;

    /**
     * 数据保留策略
     */
    private String retentionPolicy;

    /**
     * 保存时间
     */
    private String retentionPolicyTime;

    /**
     * 防止查询超时
     */
    public static final OkHttpClient.Builder CLIENT = new OkHttpClient.Builder().readTimeout(100, TimeUnit.SECONDS);

    /**
     * 策略默认值
     */
    private static String DEFAULT = "default";

    /**
     * 策略存储时间，7天
     */
    private static final String DEFAULT_POLICY_TIME = "7d";

    public AbstractInfluxdbClient() {
    }

    public AbstractInfluxdbClient(String username, String password, String url, String database, String retentionPolicy, String retentionPolicyTime) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.database = database;
        this.retentionPolicy = retentionPolicy == null || retentionPolicy.isEmpty() ? DEFAULT : retentionPolicy;
        this.retentionPolicyTime = retentionPolicyTime == null || retentionPolicyTime.isEmpty() ? DEFAULT_POLICY_TIME : retentionPolicyTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getRetentionPolicyTime() {
        return retentionPolicyTime;
    }

    public void setRetentionPolicyTime(String retentionPolicyTime) {
        this.retentionPolicyTime = retentionPolicyTime;
    }

    public static String getDEFAULT() {
        return DEFAULT;
    }

    public static void setDEFAULT(String DEFAULT) {
        AbstractInfluxdbClient.DEFAULT = DEFAULT;
    }
}