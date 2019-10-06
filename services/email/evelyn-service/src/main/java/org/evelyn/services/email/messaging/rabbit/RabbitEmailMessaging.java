package org.evelyn.services.email.messaging.rabbit;

import org.evelyn.library.messages.EmailMessage;
import org.evelyn.services.email.api.EmailService;
import org.evelyn.services.email.messaging.api.EmailMessaging;
import org.springframework.stereotype.Component;

@Component
class RabbitEmailMessaging implements EmailMessaging {
    private final EmailService emailService;

    public RabbitEmailMessaging(EmailService emailService) {
        this.emailService = emailService;
    }

    public void onMailReceived(EmailMessage emailMessage) {
        emailService.onMailSendRequest(emailMessage);
    }
}
