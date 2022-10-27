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
package com.mobaijun.oss.enums;

/**
 * software：IntelliJ IDEA 2022.1
 * enum name: OssType
 * enum description： OSS 类型
 *
 * @author MoBaiJun 2022/6/16 14:02
 */
public enum OssType {

    /**
     * 本地磁盘存储
     */
    LOCAL("local"),

    /**
     * FTP协议存储
     */
    FTP("ftp"),

    /**
     * SFTP存储
     */
    SFTP("sftp"),

    /**
     * 阿里OSS存储
     */
    ALI("aliyun"),

    /**
     * 七牛云存储
     */
    QINIU("qiniu"),

    /**
     * MinIO存储
     */
    MINIO("minio"),

    /**
     * 百度云存储
     */
    BAIDU("baidu"),

    /**
     * 腾讯云存储
     */
    TENCENT("tencent"),

    /**
     * 华为云存储
     */
    HUAWEI("huawei"),

    /**
     * 京东云存储
     */
    JD("jd"),

    /**
     * 又拍云存储
     */
    UP("up"),

    /**
     * 金山云
     */
    JINSHAN("jinshan"),

    /**
     * 网易数帆
     */
    WANGYI("wangyi"),

    /**
     * UCloud
     */
    UCLOUD("ucloud"),

    /**
     * 平安云
     */
    PINGAN("pingan"),

    /**
     * 青云
     */
    QINGYUN("qingyun"),

    /**
     * JDBC
     */
    JDBC("jdbc"),

    /**
     * 亚马逊
     */
    AWS("aws");

    /**
     * 类型
     */
    private final String value;

    OssType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}