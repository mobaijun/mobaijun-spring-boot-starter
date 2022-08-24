package com.mobaijun.email.event;

import com.mobaijun.email.model.EmailSendInfo;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailSendEvent
 * class description： 邮件发送事件
 *
 * @author MoBaiJun 2022/8/24 8:43
 */
@ToString
public class EmailSendEvent extends ApplicationEvent {
    public EmailSendEvent(EmailSendInfo sendInfo) {
        super(sendInfo);
    }
}
