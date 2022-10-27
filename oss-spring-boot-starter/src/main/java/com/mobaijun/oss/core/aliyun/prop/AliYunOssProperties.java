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
package com.mobaijun.oss.core.aliyun.prop;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: AliYunOssProperties
 * class description： 阿里云配置文件
 *
 * @author MoBaiJun 2022/6/16 14:09
 */
@ConfigurationPropertiesScan(AliYunOssProperties.PREFIX)
public class AliYunOssProperties {

    public final static String PREFIX = "aliyun";
}