/*
 * Copyright (C) 2022 www.mobaijun.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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