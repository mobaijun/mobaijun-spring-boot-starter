/*
 * Copyright (C) 2022 [www.mobaijun.com]
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
