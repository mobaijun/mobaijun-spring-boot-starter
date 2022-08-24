package com.mobaijun.oss.core.aliyun.prop;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: AliYunOssProperties
 * class description： 阿里云配置文件
 *
 * @author MoBaiJun 2022/6/16 14:09
 */
@ConfigurationPropertiesScan(AliYunOssProperties.PREFIX)
public class AliYunOssProperties {

    public final static String PREFIX = "aliyun";
}
