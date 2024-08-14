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
package com.mobaijun.sms.properties;

import com.mobaijun.sms.enums.TypeEnum;
import com.mobaijun.sms.properties.extra.Account;
import com.mobaijun.sms.properties.extra.Aliyun;
import com.mobaijun.sms.properties.extra.Tencent;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Description: [短信自定义配置文件]
 * Author: [mobaijun]
 * Date: [2024/8/14 16:55]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "spring.sms")
public class SmsProperties {

    /**
     * 类型
     */
    private TypeEnum type = TypeEnum.CUSTOM;

    /**
     * 请求路径
     */
    private String url;

    /**
     * app id
     */
    private String id;

    /**
     * app key
     */
    private String key;

    /**
     * 部分平台需要使用账号密码，都在这里配置
     */
    private Map<String, Account> accounts;

    /**
     * 腾讯云所需额外参数
     */
    @NestedConfigurationProperty
    private Tencent tencent;

    /**
     * 阿里云所需额外参数
     */
    @NestedConfigurationProperty
    private Aliyun aliyun;
}
