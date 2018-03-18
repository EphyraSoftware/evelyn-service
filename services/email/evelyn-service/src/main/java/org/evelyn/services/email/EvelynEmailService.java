package org.evelyn.services.email;

import org.evelyn.services.email.api.EmailService;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
class EvelynEmailService implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onUserCreated(UserCreatedMessage userMessage) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userMessage.email);
            mailMessage.setSubject("Welcome to Evelyn");
            mailMessage.setText("Thanks for signing up, please click on the link which doesn't exist confirm your sign up.");
            mailSender.send(mailMessage);
        }
        catch (Exception e) {
            System.out.println("Failed to send email");
            e.printStackTrace();
        }
    }
}
