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
package com.mobaijun.core;

import com.mobaijun.core.spring.SpringUtil;
import com.mobaijun.core.util.ServerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * software：IntelliJ IDEA 2022.2.3<br>
 * class name: CoreAutoConfiguration<br>
 * class description: 自动注入类<br>
 *
 * @author MoBaiJun 2022/12/7 14:17
 */
@Configuration
public class CoreAutoConfiguration {

    /**
     * 注入应用程序配置类
     *
     * @return ApplicationConfig
     */
    @Bean
    public ServerUtil applicationConfig() {
        return new ServerUtil();
    }

    /**
     * Spring 上下文工具类
     *
     * @return SpringUtil
     */
    @Bean
    public SpringUtil springContextUtils() {
        return new SpringUtil();
    }
}
