package org.evelyn.services.email.messaging.api;

import org.evelyn.library.messages.EmailMessage;

public interface EmailMessaging {
  void onMailReceived(EmailMessage emailMessage);
}
