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
package com.mobaijun.bim;

import com.mobaijun.bim.prop.BimProperties;
import com.mobaijun.bim.service.impl.BimFaceClientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: BimAutoConfiguration
 * class description： bim 自动注入类
 *
 * @author MoBaiJun 2022/10/18 15:27
 */
@Configuration
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