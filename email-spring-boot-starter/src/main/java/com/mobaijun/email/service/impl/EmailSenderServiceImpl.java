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
package com.mobaijun.email.service.impl;

import com.mobaijun.email.event.EmailSendEvent;
import com.mobaijun.email.model.EmailDetails;
import com.mobaijun.email.model.EmailSendInfo;
import com.mobaijun.email.service.EmailSenderService;
import jakarta.mail.MessagingException;
import java.io.File;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailSenderImpl
 * class description： 邮件发送实现类
 *
 * @author MoBaiJun 2022/7/7 16:20
 */
public class EmailSenderServiceImpl implements EmailSenderService {

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    private final JavaMailSender mailSender;
    private final ApplicationEventPublisher eventPublisher;
    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.properties.from}")
    private String defaultFrom;

    public EmailSenderServiceImpl(JavaMailSender mailSender, ApplicationEventPublisher eventPublisher) {
        this.mailSender = mailSender;
        this.eventPublisher = eventPublisher;
    }

    /**
     * 发送邮件
     *
     * @param emailDetails 邮件参数
     * @return EmailSendInfo 发送结果
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
            log.error("邮件发送失败: to={}, subject={}, error={}",
                    emailDetails.getTo() != null ? String.join(",", emailDetails.getTo()) : "[]",
                    emailDetails.getSubject(),
                    e.getMessage(), e);
        } finally {
            // 发布邮件发送事件
            eventPublisher.publishEvent(new EmailSendEvent(mailSendInfo));
        }
        return mailSendInfo;
    }

    /**
     * 构建复杂邮件信息类
     *
     * @param emailDetails 邮件详情
     * @throws MessagingException 邮件发送异常
     */
    private void sendMimeMail(EmailDetails emailDetails) throws MessagingException {
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
        boolean showHtml = emailDetails.getShowHtml() != null && emailDetails.getShowHtml();
        messageHelper.setText(emailDetails.getContent(), showHtml);
        if (emailDetails.getFiles() != null) {
            for (File file : emailDetails.getFiles()) {
                messageHelper.addAttachment(file.getName(), file);
            }
        }
        mailSender.send(messageHelper.getMimeMessage());
        log.info("邮件发送成功: from={}, to={}, subject={}",
                from,
                emailDetails.getTo() != null ? String.join(",", emailDetails.getTo()) : "[]",
                emailDetails.getSubject());
    }
}