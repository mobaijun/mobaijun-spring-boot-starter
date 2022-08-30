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
