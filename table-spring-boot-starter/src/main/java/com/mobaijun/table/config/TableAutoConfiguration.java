package com.mobaijun.table.config;

import com.mobaijun.table.prop.TableProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: TableAutoConfiguration
 * class description： 建表自动注入类
 *
 * @author MoBaiJun 2022/6/28 14:39
 */
@Configuration
public class TableAutoConfiguration {

    @Bean
    public TableProperties getTableProperties() {
        return new TableProperties();
    }

    @Bean
    public CreateTableConfig createTableConfig(TableProperties tableProperties) {
        return new CreateTableConfig(tableProperties);
    }
}
