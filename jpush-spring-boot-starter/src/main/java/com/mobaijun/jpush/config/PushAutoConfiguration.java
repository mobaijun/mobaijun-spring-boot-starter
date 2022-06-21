package com.mobaijun.jpush.config;

import com.mobaijun.jpush.common.PushConnection;
import com.mobaijun.jpush.prop.PushProp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: PushAutoConfiguration
 * class description： 极光推送配置类
 *
 * @author MoBaiJun 2022/6/21 10:14
 */
@Configuration
public class PushAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PushProp getPushProp() {
        return new PushProp();
    }

    /**
     * 初始化极光推算配置
     *
     * @param pushProp 配置
     * @return bean
     */
    @Bean
    @ConditionalOnMissingBean
    public PushConnection pushUtils(PushProp pushProp) {
        return new PushConnection(pushProp);
    }
}
