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
package com.mobaijun.openapi.config;

import com.mobaijun.openapi.prop.OpenAPIProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Software：IntelliJ IDEA 2021.3.2
 * ClassName: openapi
 * 类描述： openapi 配置类
 *
 * @author MoBaiJun 2022/4/26 9:09
 */
@Configuration
public class OpenAPIAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OpenAPIProperties prop() {
        return new OpenAPIProperties();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                // 配置接口文档基本信息
                .info(this.getApiInfo());
    }

    private Info getApiInfo() {
        return new Info()
                // 配置文档标题
                .title(prop().getTitle())
                // 配置文档描述
                .description(prop().getDescription())
                // 配置作者信息
                .contact(new Contact()
                        .name(prop().getContact().getAuthor())
                        .url(prop().getContact().getUrl())
                        .email(prop().getContact().getEmail()))
                // 配置License许可证信息
                .license(new License()
                        .name(prop().getLicense())
                        .url(prop().getLicenseUrl()))
                // 概述信息
                .summary(prop().getSummary())
                // 服务地址
                .termsOfService(prop().getTermsOfServiceUrl())
                // 配置版本号
                .version(prop().getVersion());
    }
}