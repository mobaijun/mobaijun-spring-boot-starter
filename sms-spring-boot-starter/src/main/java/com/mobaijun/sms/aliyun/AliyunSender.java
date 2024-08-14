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

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.mobaijun.json.utils.JsonUtil;
import com.mobaijun.sms.enums.TypeEnum;
import com.mobaijun.sms.model.SmsSenderResult;
import com.mobaijun.sms.properties.SmsProperties;
import com.mobaijun.sms.properties.extra.Aliyun;
import com.mobaijun.sms.sender.AbstractSmsSender;
import org.springframework.util.StringUtils;

/**
 * Description: [AliyunSender 是一个用于发送阿里云短信的实现类，继承自抽象短信发送类。
 * 它负责配置阿里云的短信发送客户端并通过阿里云平台发送短信。]
 * Author: [mobaijun]
 * Date: [2024/8/14 16:58]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class AliyunSender extends AbstractSmsSender<AliYunSenderParams> {

    /**
     * 阿里云短信发送客户端实例
     */
    private final Client client;

    /**
     * 阿里云的相关配置信息
     */
    private final Aliyun aliyun;

    /**
     * 构造方法，初始化阿里云短信发送客户端和配置信息。
     *
     * @param properties 包含阿里云配置信息的 SmsProperties 对象。
     * @throws Exception 如果在初始化 Client 时出现问题，则抛出异常。
     */
    public AliyunSender(SmsProperties properties) throws Exception {
        this.aliyun = properties.getAliyun();
        Config config = new Config()
                // 设置 AccessKey ID
                .setAccessKeyId(this.aliyun.getAccessKeyId())
                // 设置 AccessKey Secret
                .setAccessKeySecret(this.aliyun.getAccessKeySecret())
                // 设置访问的域名
                .setEndpoint(this.aliyun.getEndpoint());
        this.client = new Client(config);
    }

    /**
     * 获取短信发送的平台类型。
     *
     * @return 返回阿里云平台的类型枚举值。
     */
    @Override
    public TypeEnum platform() {
        return TypeEnum.ALIYUN;
    }

    /**
     * 执行短信发送操作。
     *
     * @param sp 包含阿里云短信发送所需参数的 AliYunSenderParams 对象。
     * @return 短信发送结果，封装在 SmsSenderResult 对象中。
     * @throws Exception 如果在发送短信过程中出现问题，则抛出异常。
     */
    @Override
    protected SmsSenderResult doSend(AliYunSenderParams sp) throws Exception {
        // 获取模板ID，如果传入的参数中包含模板ID，则使用它，否则使用默认模板ID
        String templateId = this.aliyun.getTemplateId();
        if (StringUtils.hasText(sp.getTemplateId())) {
            templateId = sp.getTemplateId();
        }

        // 获取签名名称，如果传入的参数中包含签名名称，则使用它，否则使用默认签名名称
        String signName = this.aliyun.getSignName();
        if (StringUtils.hasText(sp.getSignName())) {
            signName = sp.getSignName();
        }

        // 创建并配置发送短信的请求
        SendSmsRequest req = new SendSmsRequest()
                .setPhoneNumbers(String.join(SmsSenderResult.COMMA, sp.getPhoneNumbers()))
                .setSignName(signName)
                .setTemplateCode(templateId)
                .setTemplateParam(JsonUtil.toJsonString(sp.getAliyunTemplateParam()));

        // 发送短信并获取响应
        SendSmsResponse resp = this.client.sendSms(req);

        // 生成并返回短信发送结果
        return SmsSenderResult.generateAliyun(JsonUtil.toJsonString(resp), sp.toString(), sp.getPhoneNumbers());
    }
}
