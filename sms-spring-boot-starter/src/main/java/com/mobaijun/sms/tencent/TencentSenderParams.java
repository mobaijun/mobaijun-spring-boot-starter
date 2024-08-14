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
package com.mobaijun.sms.tencent;

import com.mobaijun.sms.sender.AbstractSenderParams;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [腾讯短信发送参数]
 * Author: [mobaijun]
 * Date: [2024/8/14 17:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class TencentSenderParams extends AbstractSenderParams<TencentSenderParams> {

    /**
     * 短信签名, 未指定使用 {@link com.mobaijun.sms.properties.SmsProperties#getTencent()#getSign()}
     */
    private String sign;

    /**
     * 短信模板ID, 如果未指定则使用 {@link com.mobaijun.sms.properties.SmsProperties#getTencent()#getTemplateId()}
     */
    private Integer templateId;

    /**
     * 短信模板参数
     *
     * @see com.mobaijun.sms.properties.extra.Tencent
     */
    private List<Object> templateParam = new ArrayList<>();

    /**
     * 添加模板参数
     */
    public TencentSenderParams addTemplateParam(Object param) {
        this.templateParam.add(param);
        return this;
    }
}
