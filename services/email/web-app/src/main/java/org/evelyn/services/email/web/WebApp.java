package org.evelyn.services.email.web;

import org.evelyn.services.email.api.MailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan("org.evelyn.services.email")
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "org.evelyn.email.mail")
    public MailProperties getMailProperties() {
        return new MailProperties();
    }
}
