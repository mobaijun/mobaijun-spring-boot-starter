package com.mobaijun.email.service;

import com.mobaijun.email.model.EmailDetails;
import com.mobaijun.email.model.EmailSendInfo;
import org.springframework.mail.MailSendException;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * interface name: EmailSenderService
 * interface description： 邮件发送接口
 * 接口描述： 邮件发送接口
 *
 * @author MoBaiJun 2022/7/7 16:20
 */
public interface EmailSenderService {

    /**
     * 发送邮件
     *
     * @param emailDetails 邮件信息
     * @return MailSendInfo
     */
    EmailSendInfo sendMail(EmailDetails emailDetails);

    /**
     * 发送邮件
     *
     * @param subject  主题
     * @param content  邮件正文
     * @param showHtml 是否将正文渲染为html
     * @param to       收件人，多个邮箱使用,号间隔
     * @return EmailSendInfo
     */
    default EmailSendInfo sendMail(String subject, String content, boolean showHtml, String... to) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setShowHtml(showHtml);
        emailDetails.setSubject(subject);
        emailDetails.setContent(content);
        emailDetails.setTo(to);
        return sendMail(emailDetails);
    }

    /**
     * 发送普通文本邮件
     *
     * @param subject 主题
     * @param content 邮件正文
     * @param to      收件人
     * @return EmailSendInfo
     */
    default EmailSendInfo sendTextMail(String subject, String content, String... to) {
        return sendMail(subject, content, false, to);
    }

    /**
     * 发送普通文本邮件
     *
     * @param subject 主题
     * @param content 邮件正文
     * @param to      收件人
     * @return EmailSendInfo
     */
    default EmailSendInfo sendTextMail(String subject, String content, List<String> to) {
        return sendMail(subject, content, false, to.toArray(new String[0]));
    }

    /**
     * 发送Html邮件
     *
     * @param subject 主题
     * @param content 邮件正文
     * @param to      收件人
     * @return EmailSendInfo
     */
    default EmailSendInfo sendHtmlMail(String subject, String content, String... to) {
        return sendMail(subject, content, true, to);
    }

    /**
     * 发送Html邮件
     *
     * @param subject 主题
     * @param content 邮件正文
     * @param to      收件人
     * @return EmailSendInfo 邮件发送结果信息
     */
    default EmailSendInfo sendHtmlMail(String subject, String content, List<String> to) {
        return sendHtmlMail(subject, content, to.toArray(new String[0]));
    }

    /**
     * 检查邮件是否符合标准
     *
     * @param emailDetails 邮件信息
     */
    default void checkMail(EmailDetails emailDetails) {
        boolean noTo = emailDetails.getTo() == null || emailDetails.getTo().length == 0;
        boolean noCc = emailDetails.getCc() == null || emailDetails.getCc().length == 0;
        boolean noBcc = emailDetails.getBcc() == null || emailDetails.getBcc().length == 0;
        if (noTo && noCc && noBcc) {
            throw new MailSendException("The email should have at least one recipient");
        }
        if (!StringUtils.hasText(emailDetails.getSubject())) {
            throw new MailSendException("The subject of the email cannot be empty");
        }
        if (!StringUtils.hasText(emailDetails.getContent())) {
            throw new MailSendException("The content of the email cannot be empty");
        }
    }
}