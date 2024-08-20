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
package com.mobaijun.xxljob.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Description: XXL作业属性
 * Author: [mobaijun]
 * Date: [2024/8/20 9:52]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = XxlJobProperties.PREFIX)
public class XxlJobProperties {

    public static final String PREFIX = "xxl.job";

    /**
     * 是否启用分布式调度任务，默认：开启
     */
    private boolean enabled = true;

    /**
     * 执行器通讯TOKEN [选填]：非空时启用；
     */
    private String accessToken;

    @NestedConfigurationProperty
    private XxlAdminProperties admin = new XxlAdminProperties();

    @NestedConfigurationProperty
    private XxlExecutorProperties executor = new XxlExecutorProperties();
}
