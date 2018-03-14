package org.evelyn.services.email.api;

import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;

public interface EmailService {
    String onUserCreated(UserCreatedMessage userMessage);
}
