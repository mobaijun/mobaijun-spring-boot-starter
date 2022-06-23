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
