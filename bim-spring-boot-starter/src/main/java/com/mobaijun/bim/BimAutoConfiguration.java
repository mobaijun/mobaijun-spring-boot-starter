package com.mobaijun.bim;

import com.mobaijun.bim.prop.BimProperties;
import com.mobaijun.bim.service.impl.BimFaceClientServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BimAutoConfiguration
 * class description： bim 自动注入类
 *
 * @author MoBaiJun 2022/10/18 15:27
 */
@Configurable
public class BimAutoConfiguration {

    @Bean
    public BimProperties bimProperties() {
        return new BimProperties();
    }

    @Bean
    public BimFaceClientServiceImpl bimFaceClientService() {
        return new BimFaceClientServiceImpl(bimProperties());
    }
}
