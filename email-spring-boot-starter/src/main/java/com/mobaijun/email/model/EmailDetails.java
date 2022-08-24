package com.mobaijun.email.model;

import lombok.Data;

import java.io.File;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MailDetails
 * class description：邮件详情
 *
 * @author MoBaiJun 2022/8/24 8:44
 */
@Data
public class EmailDetails {
    /**
     * 发件人
     */
    private String from;

    /**
     * 收件人
     */
    private String[] to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 是否渲染html
     */
    private Boolean showHtml;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 抄送
     */
    private String[] cc;

    /**
     * 密送
     */
    private String[] bcc;

    /**
     * 附件
     */
    private File[] files;
}
