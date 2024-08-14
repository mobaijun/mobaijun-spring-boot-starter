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
package com.mobaijun.sms.aliyun;

import com.mobaijun.sms.sender.AbstractSenderParams;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [阿里云短信发送扩展]
 * Author: [mobaijun]
 * Date: [2024/8/14 16:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class AliYunSenderParams extends AbstractSenderParams<AliYunSenderParams> {

    /**
     * 短信签名名称, 未指定使用 {@link com.mobaijun.sms.properties.SmsProperties#getAliyun()#getSignName()}
     */
    private String signName;

    /**
     * 短信模板ID, 如果未指定则使用 {@link com.mobaijun.sms.properties.SmsProperties#getAliyun()#getTemplateId()}
     */
    private String templateId;

    /**
     * 短信模板参数
     */
    private Map<String, Object> aliyunTemplateParam = new HashMap<>();
}
