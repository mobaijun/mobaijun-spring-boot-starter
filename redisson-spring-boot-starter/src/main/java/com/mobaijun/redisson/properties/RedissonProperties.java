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
package com.mobaijun.redisson.properties;

import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description: [Redisson 配置属性]
 * Author: [mobaijun]
 * Date: [2024/8/14 18:30]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    /**
     * redis缓存key前缀
     */
    private String keyPrefix;

    /**
     * 线程池数量,默认值 = 当前处理核数量 * 2
     */
    private int threads;

    /**
     * Netty线程池数量,默认值 = 当前处理核数量 * 2
     */
    private int nettyThreads;

    /**
     * 单机服务配置
     */
    private SingleServerConfig singleServerConfig;

    /**
     * 集群服务配置
     */
    private ClusterServersConfig clusterServersConfig;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    @Override
    public String toString() {
        return "RedissonProperties{" +
                "keyPrefix='" + keyPrefix + '\'' +
                ", threads=" + threads +
                ", nettyThreads=" + nettyThreads +
                ", singleServerConfig=" + singleServerConfig +
                ", clusterServersConfig=" + clusterServersConfig +
                '}';
    }

    public static class SingleServerConfig {

        /**
         * 客户端名称
         */
        private String clientName;

        /**
         * 最小空闲连接数
         */
        private int connectionMinimumIdleSize;

        /**
         * 连接池大小
         */
        private int connectionPoolSize;

        /**
         * 连接空闲超时，单位：毫秒
         */
        private int idleConnectionTimeout;

        /**
         * 命令等待超时，单位：毫秒
         */
        private int timeout;

        /**
         * 发布和订阅连接池大小
         */
        private int subscriptionConnectionPoolSize;

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public int getConnectionMinimumIdleSize() {
            return connectionMinimumIdleSize;
        }

        public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
            this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        }

        public int getConnectionPoolSize() {
            return connectionPoolSize;
        }

        public void setConnectionPoolSize(int connectionPoolSize) {
            this.connectionPoolSize = connectionPoolSize;
        }

        public int getIdleConnectionTimeout() {
            return idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getSubscriptionConnectionPoolSize() {
            return subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        @Override
        public String toString() {
            return "SingleServerConfig{" +
                    "clientName='" + clientName + '\'' +
                    ", connectionMinimumIdleSize=" + connectionMinimumIdleSize +
                    ", connectionPoolSize=" + connectionPoolSize +
                    ", idleConnectionTimeout=" + idleConnectionTimeout +
                    ", timeout=" + timeout +
                    ", subscriptionConnectionPoolSize=" + subscriptionConnectionPoolSize +
                    '}';
        }
    }

    public static class ClusterServersConfig {

        /**
         * 客户端名称
         */
        private String clientName;

        /**
         * master最小空闲连接数
         */
        private int masterConnectionMinimumIdleSize;

        /**
         * master连接池大小
         */
        private int masterConnectionPoolSize;

        /**
         * slave最小空闲连接数
         */
        private int slaveConnectionMinimumIdleSize;

        /**
         * slave连接池大小
         */
        private int slaveConnectionPoolSize;

        /**
         * 连接空闲超时，单位：毫秒
         */
        private int idleConnectionTimeout;

        /**
         * 命令等待超时，单位：毫秒
         */
        private int timeout;

        /**
         * 发布和订阅连接池大小
         */
        private int subscriptionConnectionPoolSize;

        /**
         * 读取模式
         */
        private ReadMode readMode;

        /**
         * 订阅模式
         */
        private SubscriptionMode subscriptionMode;

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public int getMasterConnectionMinimumIdleSize() {
            return masterConnectionMinimumIdleSize;
        }

        public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
            this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
        }

        public int getMasterConnectionPoolSize() {
            return masterConnectionPoolSize;
        }

        public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
            this.masterConnectionPoolSize = masterConnectionPoolSize;
        }

        public int getSlaveConnectionMinimumIdleSize() {
            return slaveConnectionMinimumIdleSize;
        }

        public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
            this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
        }

        public int getSlaveConnectionPoolSize() {
            return slaveConnectionPoolSize;
        }

        public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
            this.slaveConnectionPoolSize = slaveConnectionPoolSize;
        }

        public int getIdleConnectionTimeout() {
            return idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getSubscriptionConnectionPoolSize() {
            return subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public ReadMode getReadMode() {
            return readMode;
        }

        public void setReadMode(ReadMode readMode) {
            this.readMode = readMode;
        }

        public SubscriptionMode getSubscriptionMode() {
            return subscriptionMode;
        }

        public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
            this.subscriptionMode = subscriptionMode;
        }

        @Override
        public String toString() {
            return "ClusterServersConfig{" +
                    "clientName='" + clientName + '\'' +
                    ", masterConnectionMinimumIdleSize=" + masterConnectionMinimumIdleSize +
                    ", masterConnectionPoolSize=" + masterConnectionPoolSize +
                    ", slaveConnectionMinimumIdleSize=" + slaveConnectionMinimumIdleSize +
                    ", slaveConnectionPoolSize=" + slaveConnectionPoolSize +
                    ", idleConnectionTimeout=" + idleConnectionTimeout +
                    ", timeout=" + timeout +
                    ", subscriptionConnectionPoolSize=" + subscriptionConnectionPoolSize +
                    ", readMode=" + readMode +
                    ", subscriptionMode=" + subscriptionMode +
                    '}';
        }
    }
}
