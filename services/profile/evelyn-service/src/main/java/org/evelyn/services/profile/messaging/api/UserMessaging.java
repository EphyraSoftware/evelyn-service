package org.evelyn.services.profile.messaging.api;

import org.evelyn.library.messages.EmailMessage;

public interface UserMessaging {
    void sendUserRegistered(EmailMessage userRegisteredMessage);
}
