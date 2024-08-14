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

import com.mobaijun.json.utils.JsonUtil;
import com.mobaijun.sms.enums.TypeEnum;
import com.mobaijun.sms.model.SmsSenderResult;
import com.mobaijun.sms.properties.SmsProperties;
import com.mobaijun.sms.properties.extra.Tencent;
import com.mobaijun.sms.sender.AbstractSmsSender;
import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Description: [
 * TencentSender 是一个用于发送腾讯云短信的实现类，继承自抽象短信发送类。
 * 负责配置腾讯云的短信发送客户端并通过腾讯云平台发送短信。]
 * Author: [mobaijun]
 * Date: [2024/8/14 17:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class TencentSender extends AbstractSmsSender<TencentSenderParams> {

    /**
     * 腾讯云的相关配置信息
     */
    private final Tencent tencent;

    /**
     * 腾讯云的身份验证凭证
     */
    private final Credential cred;

    /**
     * 构造方法，初始化腾讯云的配置信息和身份验证凭证。
     *
     * @param properties 包含腾讯云配置信息的 SmsProperties 对象。
     */
    public TencentSender(SmsProperties properties) {
        this.tencent = properties.getTencent();
        this.cred = new Credential(properties.getId(), properties.getKey());
    }

    /**
     * 获取短信发送的平台类型。
     *
     * @return 返回腾讯云平台的类型枚举值。
     */
    @Override
    public TypeEnum platform() {
        return TypeEnum.TENCENT;
    }

    /**
     * 执行短信发送操作。
     *
     * @param sp 包含腾讯云短信发送所需参数的 TencentSenderParams 对象。
     * @return 短信发送结果，封装在 SmsSenderResult 对象中。
     * @throws Exception 如果在发送短信过程中出现问题，则抛出异常。
     */
    @Override
    protected SmsSenderResult doSend(TencentSenderParams sp) throws Exception {
        // 配置 HTTP 访问的域名
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(this.tencent.getEndpoint());

        // 配置客户端的 HTTP 配置和客户端配置
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        // 创建腾讯云短信客户端实例
        SmsClient client = new SmsClient(this.cred, this.tencent.getRegion(), clientProfile);

        // 创建请求参数的 JSON 映射
        Map<String, Object> json = new HashMap<>(5);
        json.put("PhoneNumberSet", sp.getPhoneNumbers());
        json.put("SmsSdkAppid", this.tencent.getSdkId());

        // 填充签名信息
        fillSign(sp, json);
        // 填充模板信息
        fillTemplate(sp, json);

        // 生成短信发送请求并发送
        SendSmsRequest req = AbstractModel.fromJsonString(JsonUtil.toJsonString(json), SendSmsRequest.class);
        SendSmsResponse resp = client.SendSms(req);

        // 生成并返回短信发送结果
        return SmsSenderResult.generateTencent(AbstractModel.toJsonString(resp), sp.toString(), sp.getPhoneNumbers());
    }

    /**
     * 填充签名信息到请求参数 JSON 中。
     *
     * @param sp   包含签名信息的 TencentSenderParams 对象。
     * @param json 请求参数的 JSON 映射。
     */
    protected void fillSign(TencentSenderParams sp, Map<String, Object> json) {
        String sign = StringUtils.hasText(sp.getSign()) ? sp.getSign() : this.tencent.getSign();
        if (!StringUtils.hasText(sign)) {
            return;
        }
        json.put("Sign", sign);
    }

    /**
     * 填充模板信息到请求参数 JSON 中。
     *
     * @param sp   包含模板信息的 TencentSenderParams 对象。
     * @param json 请求参数的 JSON 映射。
     */
    protected void fillTemplate(TencentSenderParams sp, Map<String, Object> json) {
        Integer templateId = sp.getTemplateId() != null ? sp.getTemplateId() : this.tencent.getTemplateId();
        if (templateId == null) {
            return;
        }
        json.put("TemplateID", templateId);

        // 如果模板参数不为空，则添加到请求参数 JSON 中
        if (!CollectionUtils.isEmpty(sp.getTemplateParam())) {
            json.put("TemplateParamSet", sp.getTemplateParam());
        }
    }
}
