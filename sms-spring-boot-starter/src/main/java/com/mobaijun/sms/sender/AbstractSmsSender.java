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

import com.mobaijun.sms.enums.TypeEnum;
import com.mobaijun.sms.model.SmsSenderResult;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Description: [
 * <p>
 * AbstractSmsSender 是一个用于发送短信的抽象类。
 * <p>
 * 它提供了一个发送短信的模板方法，处理异常，并使用 SLF4J 日志框架记录错误信息。
 * <p>
 *
 * @param <P> 一个继承自 AbstractSenderParams 的类型参数。它允许灵活地指定发送短信所需的参数。
 *            <p>]
 *            Author: [mobaijun]
 *            Date: [2024/8/14 16:59]
 *            IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@SuppressWarnings("java:S112")
public abstract class AbstractSmsSender<P extends AbstractSenderParams<P>> {

    /**
     * 发送短信的方法。
     *
     * @param p 参数配置，包含了发送短信所需的所有信息。
     * @return 短信发送结果，封装了发送状态、消息ID等信息。
     */
    public SmsSenderResult send(P p) {
        TypeEnum platform = platform();
        try {
            return doSend(p);
        } catch (Exception e) {
            String msg = platform.name() + "平台发送短信出现异常!";
            return errRet(platform, p.getPhoneNumbers(), msg, e);
        }
    }

    /**
     * 异常返回处理，当发送短信出现异常时调用此方法。
     *
     * @param platform     发送短信的平台类型。
     * @param phoneNumbers 接收短信的手机号码集合。
     * @param msg          异常消息。
     * @param e            捕获的异常对象。
     * @return 封装了异常信息的短信发送结果。
     */
    public SmsSenderResult errRet(TypeEnum platform, Set<String> phoneNumbers, String msg, Exception e) {
        String id = ObjectId.get().toString();
        log.error("sms result error! errorId: {}; {}", id, msg, e);
        return SmsSenderResult.generateException(platform, phoneNumbers, id, e);
    }

    /**
     * 抽象方法，用于获取执行短信发送的平台类型。
     * 实现此类的子类必须定义此方法以指定平台类型。
     *
     * @return 平台类型，作为 TypeEnum 的值。
     */
    public abstract TypeEnum platform();

    /**
     * 执行发送短信的具体操作。
     *
     * @param p 参数配置，包含了发送短信所需的所有信息。
     * @return 短信发送操作的结果，封装在 SmsSenderResult 对象中。
     * @throws Exception 如果在发送短信的过程中出现任何异常，则抛出。
     */
    protected abstract SmsSenderResult doSend(P p) throws Exception;
}
