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
package com.mobaijun.jpush.config;

import com.mobaijun.jpush.prop.PushProp;
import com.mobaijun.jpush.util.PushUtil;
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
    public PushUtil pushUtil(PushProp pushProp) {
        return new PushUtil(pushProp);
    }
}