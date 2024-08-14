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
package com.mobaijun.sms;

import com.mobaijun.sms.aliyun.AliyunSender;
import com.mobaijun.sms.properties.SmsProperties;
import com.mobaijun.sms.sender.AbstractSmsSender;
import com.mobaijun.sms.tencent.TencentSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Description: [短信自动注入类]
 * Author: [mobaijun]
 * Date: [2024/8/14 17:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties({SmsProperties.class})
public class SmsAutoConfiguration {

    /**
     * 保存 SMS 配置信息的对象
     */
    private final SmsProperties properties;

    /**
     * 创建并配置腾讯云短信发送器的 Bean。
     * <p>
     * 只有当配置文件中 `spring.sms.type` 配置为 "TENCENT" 时，才会加载此 Bean。
     * 如果已经存在 `AbstractSmsSender` 的 Bean，则不会创建新的腾讯云短信发送器 Bean。
     *
     * @return 返回一个配置好的 `TencentSender` 实例。
     */
    @Bean
    @ConditionalOnMissingBean(AbstractSmsSender.class)
    @ConditionalOnProperty(name = "spring.sms.type", havingValue = "TENCENT")
    public TencentSender tencentSmsSender() {
        return new TencentSender(this.properties);
    }

    /**
     * 创建并配置阿里云短信发送器的 Bean。
     * <p>
     * 只有当配置文件中 `spring.sms.type` 配置为 "ALIYUN" 时，才会加载此 Bean。
     * 如果已经存在 `AbstractSmsSender` 的 Bean，则不会创建新的阿里云短信发送器 Bean。
     * 该方法使用 `@SneakyThrows` 注解来自动处理 `Exception` 异常。
     *
     * @return 返回一个配置好的 `AliyunSender` 实例。
     */
    @Bean
    @SneakyThrows(Exception.class)
    @ConditionalOnMissingBean(AbstractSmsSender.class)
    @ConditionalOnProperty(name = "spring.sms.type", havingValue = "ALIYUN")
    public AliyunSender aliyunSmsSender() {
        return new AliyunSender(this.properties);
    }
}
