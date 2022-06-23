package com.mobaijun.oss.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: OssProperties
 * class description： 配置文件
 *
 * @author MoBaiJun 2022/6/16 13:59
 */
@ConfigurationProperties(OssProperties.PREFIX)
@EnableConfigurationProperties(OssProperties.class)
public class OssProperties {

    /**
     * 标识
     */
    public static final String PREFIX = "oss";
}
