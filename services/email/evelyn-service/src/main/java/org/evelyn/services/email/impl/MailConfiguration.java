package org.evelyn.services.email.impl;

import org.evelyn.services.email.api.MailProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {
    @Value("${org.evelyn.email.mail.host}")
    private String mailHost;

    @Value("${org.evelyn.email.mail.port}")
    private int mailPort;

    @Value("${org.evelyn.email.mail.username}")
    private String username;

    @Value("${org.evelyn.email.mail.password}")
    private String password;

    // Extra properties to be provided as Java Mail properties.
    private final MailProperties mailProperties;

    public MailConfiguration(ObjectProvider<MailProperties> mailProperties) {
        this.mailProperties = mailProperties.getIfAvailable();
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        if (mailProperties != null) {
            Properties props = mailSender.getJavaMailProperties();
            mailProperties.getProperties().forEach(props::put);
        }

        return mailSender;
    }
}
