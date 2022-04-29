package com.mobaijun.influxdb.config;

import com.mobaijun.influxdb.core.constant.Constant;
import com.mobaijun.influxdb.prop.InfluxProperties;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(value = {InfluxProperties.class})
@ConditionalOnProperty(prefix = InfluxProperties.PREFIX, value = Constant.ENABLE, matchIfMissing = true)
public class InfluxdbAutoConfiguration {

    public InfluxdbAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(InfluxProperties.class)
    public InfluxProperties getInfluxProperties() {
        return new InfluxProperties();
    }

    /**
     * 初始化influxdb
     *
     * @param influxProp 初始化属性
     * @return 配置属性
     */
    @Bean
    @ConditionalOnMissingBean(InfluxdbConnection.class)
    public InfluxdbConnection getInfluxdbConnection(InfluxProperties influxProp) {
        return new InfluxdbConnection(
                influxProp.getUsername(),
                influxProp.getPassword(),
                influxProp.getUrl(),
                influxProp.getDatabase(),
                influxProp.getRetentionPolicy(),
                influxProp.getRetentionPolicyTime());
    }

    @Bean
    @ConditionalOnMissingBean
    public InfluxDB influxdb(InfluxProperties properties) {
        InfluxDB influxDB = InfluxDBFactory.connect(properties.getUrl(), properties.getUsername(), properties.getPassword());
        influxDB.setDatabase(properties.getDatabase());
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }
}
