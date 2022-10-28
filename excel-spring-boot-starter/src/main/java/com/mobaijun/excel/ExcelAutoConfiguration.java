package com.mobaijun.excel;

import com.mobaijun.excel.service.impl.ExcelServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: ExcelAutoConfiguration
 * class description： excel 自动注入类
 *
 * @author MoBaiJun 2022/10/28 13:48
 */
@Configuration
public class ExcelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ExcelServiceImpl excelService() {
        return new ExcelServiceImpl();
    }
}
