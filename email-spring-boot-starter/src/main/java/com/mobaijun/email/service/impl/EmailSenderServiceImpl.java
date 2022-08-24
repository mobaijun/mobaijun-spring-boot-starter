package com.mobaijun.email.service.impl;

import com.mobaijun.email.event.EmailSendEvent;
import com.mobaijun.email.model.EmailDetails;
import com.mobaijun.email.model.EmailSendInfo;
import com.mobaijun.email.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailSenderImpl
 * class description： 邮件发送实现类
 *
 * @author MoBaiJun 2022/7/7 16:20
 */
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.properties.from}")
    private String defaultFrom;

    /**
     * 发送邮件
     *
     * @param emailDetails 邮件参数
     * @return boolean 发送是否成功
     */
    @Override
    public EmailSendInfo sendMail(EmailDetails emailDetails) {
        EmailSendInfo mailSendInfo = new EmailSendInfo(emailDetails);
        mailSendInfo.setSentDate(LocalDateTime.now());
        try {
            // 1.检测邮件
            checkMail(emailDetails);
            // 2.发送邮件
            sendMimeMail(emailDetails);
            mailSendInfo.setSuccess(true);
        } catch (Exception e) {
            mailSendInfo.setSuccess(false);
            mailSendInfo.setErrorMsg(e.getMessage());
            log.error("发送邮件失败: [{}]", emailDetails, e);
        } finally {
            // 发布邮件发送事件
            eventPublisher.publishEvent(new EmailSendEvent(mailSendInfo));
        }
        return mailSendInfo;
    }

    /**
     * 构建复杂邮件信息类
     *
     * @param emailDetails 邮件发送设置
     */
    private void sendMimeMail(EmailDetails emailDetails) throws MessagingException {
        // true表示支持复杂类型
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
        String from = StringUtils.hasText(emailDetails.getFrom()) ? emailDetails.getFrom() : defaultFrom;
        messageHelper.setFrom(from);
        messageHelper.setSubject(emailDetails.getSubject());
        if (emailDetails.getTo() != null && emailDetails.getTo().length > 0) {
            messageHelper.setTo(emailDetails.getTo());
        }
        if (emailDetails.getCc() != null && emailDetails.getCc().length > 0) {
            messageHelper.setCc(emailDetails.getCc());
        }
        if (emailDetails.getBcc() != null && emailDetails.getBcc().length > 0) {
            messageHelper.setBcc(emailDetails.getBcc());
        }
        // 是否展示html
        boolean showHtml = emailDetails.getShowHtml() != null && emailDetails.getShowHtml();
        messageHelper.setText(emailDetails.getContent(), showHtml);
        if (emailDetails.getFiles() != null) {
            for (File file : emailDetails.getFiles()) {
                messageHelper.addAttachment(file.getName(), file);
            }
        }
        mailSender.send(messageHelper.getMimeMessage());
        log.info("发送邮件成功：[{}]", emailDetails);
    }
}
