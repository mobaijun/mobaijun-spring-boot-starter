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
package com.mobaijun.sms.model;

import com.mobaijun.json.util.JsonUtil;
import com.mobaijun.sms.enums.TypeEnum;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Description: [封装的短信返回体]
 * Author: [mobaijun]
 * Date: [2024/8/14 16:53]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmsSenderResult {

    /**
     * 分隔符
     */
    public static final String COMMA = ",";

    /**
     * 短信平台
     *
     * @see TypeEnum
     */
    protected String platform;

    /**
     * 发送的目标
     */
    protected Set<String> target;

    /**
     * 是否发送成功
     */
    protected boolean success;

    /**
     * 提示信息
     */
    protected String msg;

    /**
     * 请求的详细信息-各平台自定义
     */
    protected String req;

    /**
     * 返回结果信息
     */
    protected String res;

    /**
     * 出现异常返回结果
     *
     * @param platform     平台
     * @param phoneNumbers 目标手机号
     * @param id           异常id
     * @param e            异常信息
     * @return 短信发送结果
     */
    public static SmsSenderResult generateException(TypeEnum platform, Set<String> phoneNumbers, String id,
                                                    Throwable e) {
        SmsSenderResult result = new SmsSenderResult();
        result.success = false;
        result.msg = "短信发送失败，出现异常:" + e.getMessage() + COMMA + id;
        result.target = phoneNumbers;
        result.platform = platform.name();
        return result;
    }

    /**
     * 生成结果数据
     *
     * @param resp         请求的返回结果
     * @param phoneNumbers 目标号码
     */
    public static SmsSenderResult generateTencent(String resp, String req, Set<String> phoneNumbers) {
        SmsSenderResult result = new SmsSenderResult();
        result.res = resp;
        // 没有异常就是成功!
        result.success = true;
        result.platform = TypeEnum.TENCENT.name();
        result.target = phoneNumbers;
        result.req = req;
        return result;
    }

    /**
     * 生成一个表示阿里云短信发送结果的 SmsSenderResult 对象。
     *
     * @param resp         短信发送的响应内容，通常是服务器返回的响应信息。
     * @param req          短信发送的请求内容，通常是发送请求的详细信息。
     * @param phoneNumbers 目标手机号集合，即此次短信发送的目标号码列表。
     * @return 一个表示阿里云短信发送结果的 SmsSenderResult 对象。
     */
    public static SmsSenderResult generateAliyun(String resp, String req, Set<String> phoneNumbers) {
        SmsSenderResult result = new SmsSenderResult();
        result.res = resp;
        // 没有异常就是成功!
        result.success = true;
        result.platform = TypeEnum.ALIYUN.name();
        result.target = phoneNumbers;
        result.req = req;
        return result;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
