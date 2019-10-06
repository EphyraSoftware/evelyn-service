package org.evelyn.services.email.impl;

import org.evelyn.library.messages.EmailMessage;
import org.evelyn.services.email.api.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
class EvelynEmailService implements EmailService {
    private final JavaMailSender mailSender;

    public EvelynEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void onMailSendRequest(EmailMessage emailMessage) {
        try {
            MimeMessagePreparator preparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

                messageHelper.setTo(emailMessage.getRecipients().toArray(new String[0]));
                messageHelper.setSubject(emailMessage.getSubject());

                messageHelper.setText(emailMessage.getBody(), true);
            };

            mailSender.send(preparator);
        }
        catch (Exception e) {
            System.out.println("Failed to send email");
            e.printStackTrace();
        }
    }
}
