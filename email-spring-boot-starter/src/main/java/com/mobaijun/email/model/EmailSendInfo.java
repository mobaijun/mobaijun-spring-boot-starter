package com.mobaijun.email.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailSendInfo
 * class description： 邮件发送详情
 *
 * @author MoBaiJun 2022/8/24 8:45
 */
@Data
public class EmailSendInfo {

    /**
     * 邮件信息
     */
    private final EmailDetails emailDetails;

    /**
     * 发送时间
     */
    private LocalDateTime sentDate;

    /**
     * 是否发送成功
     */
    private Boolean success;

    /**
     * 错误信息 errorMsg
     */
    private String errorMsg;

    public EmailSendInfo(EmailDetails emailDetails) {
        this.emailDetails = emailDetails;
    }
}
