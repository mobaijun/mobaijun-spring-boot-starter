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
package com.mobaijun.sms.properties.extra;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [腾讯短信配置项]
 * Author: [mobaijun]
 * Date: [2024/8/14 16:57]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class Tencent {

    /**
     * 调用api参数所需的 sdk ip
     */
    private int sdkId;

    /**
     * 配置节点
     */
    private String endpoint = "sms.tencentcloudapi.com";

    /**
     * 分区
     */
    private String region = "";

    /**
     * 短信模板id
     */
    private Integer templateId = null;

    /**
     * 短信签名，发往国内必填
     */
    private String sign = null;
}
