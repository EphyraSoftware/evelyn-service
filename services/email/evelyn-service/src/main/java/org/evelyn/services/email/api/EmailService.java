package org.evelyn.services.email.api;

import org.evelyn.library.messages.EmailMessage;

public interface EmailService {
    void onMailSendRequest(EmailMessage userMessage);
}
