package com.mobaijun.influxdb.config;

import com.mobaijun.influxdb.prop.InfluxProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class InfluxDbAutoConfiguration {
    /**
     * 初始化属性
     *
     * @return 数据
     */
    @Bean
    @ConditionalOnMissingBean
    public InfluxProperties getInfluxProp() {
        return new InfluxProperties();
    }

    /**
     * 初始化influxdb
     *
     * @param influxProp 初始化属性
     * @return 配置属性
     */
    @Bean
    public InfluxDbConnection getInfluxDb(InfluxProperties influxProp) {
        return new InfluxDbConnection(
                influxProp.getUsername(),
                influxProp.getPassword(),
                influxProp.getUrl(),
                influxProp.getDatabase(),
                influxProp.getRetentionPolicy(),
                influxProp.getRetentionPolicyTime());
    }
}
