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
package com.mobaijun.sms.sender;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * AbstractSenderParams 是一个用于封装短信发送参数的抽象类。
 * 它包含了下发手机号码、短信内容和国家代码等信息，并提供了添加手机号码的方法。
 *
 * @param <P> 一个继承自 AbstractSenderParams 的类型参数。用于实现链式调用返回当前对象的实例。
 */
@Getter
@Setter
public abstract class AbstractSenderParams<P extends AbstractSenderParams<P>> {

    /**
     * 下发手机号码，采用 e.164 标准，格式为+[国家或地区码][手机号]。
     * 单次请求最多支持200个手机号，且要求全为境内手机号或全为境外手机号。
     * 例如：+8613711112222，其中+号表示国际直拨，86为国家码，13711112222为手机号。
     */
    private Set<String> phoneNumbers = new HashSet<>();

    /**
     * 短信内容
     */
    private String content;

    /**
     * 国家代码，例如 CN 表示中国
     */
    private String country;

    /**
     * 添加手机号到 phoneNumbers 集合中。
     *
     * @param phone 要添加的手机号，格式应符合 e.164 标准。
     * @return 返回当前对象实例，以便实现链式调用。
     */
    @SuppressWarnings("unchecked")
    public P addPhone(String phone) {
        this.phoneNumbers.add(phone);
        return (P) this;
    }
}
