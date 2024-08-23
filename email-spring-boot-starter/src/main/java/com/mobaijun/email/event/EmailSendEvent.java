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
package com.mobaijun.email.event;

import com.mobaijun.email.model.EmailSendInfo;
import org.springframework.context.ApplicationEvent;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailSendEvent
 * class description： 邮件发送事件
 *
 * @author MoBaiJun 2022/8/24 8:43
 */
public class EmailSendEvent extends ApplicationEvent {

    public EmailSendEvent(EmailSendInfo sendInfo) {
        super(sendInfo);
    }

    @Override
    public String toString() {
        return "EmailSendEvent{" +
                "source=" + source +
                '}';
    }
}