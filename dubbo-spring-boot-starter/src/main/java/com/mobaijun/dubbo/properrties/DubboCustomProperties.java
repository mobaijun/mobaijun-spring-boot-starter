package com.mobaijun.dubbo.properrties;

import com.mobaijun.dubbo.enums.RequestLogEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Description: [dubbo 自定义配置]
 * Author: [mobaijun]
 * Date: [2024/8/5 11:56]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@RefreshScope
@AllArgsConstructor
@ConfigurationProperties(prefix = "dubbo.custom")
public class DubboCustomProperties {

    /**
     * 是否开启请求日志记录
     */
    private Boolean requestLog;

    /**
     * 日志级别
     */
    private RequestLogEnum logLevel;
}
