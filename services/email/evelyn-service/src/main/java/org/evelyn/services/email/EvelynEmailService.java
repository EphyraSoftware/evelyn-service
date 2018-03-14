package org.evelyn.services.email;

import org.evelyn.services.email.api.EmailService;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.stereotype.Component;

@Component
class EvelynEmailService implements EmailService {
    @Override
    public String onUserCreated(UserCreatedMessage userMessage) {
        System.out.println(userMessage);
        return "";
    }
}
