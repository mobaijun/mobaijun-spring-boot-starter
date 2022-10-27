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
package com.mobaijun.file.prop;

import com.mobaijun.file.ftp.FtpMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.nio.charset.StandardCharsets;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: FileProperties
 * class description： 文件配置属性
 *
 * @author MoBaiJun 2022/9/26 14:34
 */
@EnableConfigurationProperties(FileProperties.class)
@ConfigurationProperties(prefix = FileProperties.PREFIX)
public class FileProperties {

    public static final String PREFIX = "april.file";

    /**
     * ftp
     */
    public static final String PREFIX_FTP = PREFIX + ".ftp";

    /**
     * localhost
     */
    public static final String PREFIX_LOCAL = PREFIX + ".local";

    /**
     * localhost
     */
    private LocalProperties local;

    /**
     * ftp
     */
    private FtpProperties ftp;

    public static class LocalProperties {

        /**
         * 本地文件存放原始路径
         * 如果为Null, 默认存放在系统临时目录下
         */
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class FtpProperties {

        /**
         * 目标ip
         */
        private String ip;

        /**
         * ftp端口
         */
        private Integer port = 21;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * ftp路径
         */
        private String path = "/";

        /**
         * 模式
         */
        private FtpMode mode;

        /**
         * 字符集
         */
        private String encoding = StandardCharsets.UTF_8.name();

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public FtpMode getMode() {
            return mode;
        }

        public void setMode(FtpMode mode) {
            this.mode = mode;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }
    }

    public LocalProperties getLocal() {
        return local;
    }

    public void setLocal(LocalProperties local) {
        this.local = local;
    }

    public FtpProperties getFtp() {
        return ftp;
    }

    public void setFtp(FtpProperties ftp) {
        this.ftp = ftp;
    }
}