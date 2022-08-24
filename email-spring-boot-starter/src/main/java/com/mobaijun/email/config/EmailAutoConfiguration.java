package com.mobaijun.email.config;

import com.mobaijun.email.service.EmailSenderService;
import com.mobaijun.email.service.impl.EmailSenderServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: EmailAutoConfiguration
 * class description： 邮件 配置类
 *
 * @author MoBaiJun 2022/7/7 16:19
 */
@AutoConfiguration(after = MailSenderAutoConfiguration.class)
public class EmailAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(EmailSenderService.class)
    @ConditionalOnProperty(prefix = "spring.mail", name = "host")
    public EmailSenderService mailSenderImpl(JavaMailSender javaMailSender,
                                             ApplicationEventPublisher applicationEventPublisher) {
        return new EmailSenderServiceImpl(javaMailSender, applicationEventPublisher);
    }
}
