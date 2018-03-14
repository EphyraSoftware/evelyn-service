package org.evelyn.services.email.messaging.rabbit;

import org.evelyn.services.email.api.EmailService;
import org.evelyn.services.email.messaging.api.EmailMessaging;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RabbitEmailMessaging implements EmailMessaging {
    @Autowired
    private EmailService emailService;

    public void onUserCreated(UserCreatedMessage userMessage) {
        emailService.onUserCreated(userMessage);
    }
}
