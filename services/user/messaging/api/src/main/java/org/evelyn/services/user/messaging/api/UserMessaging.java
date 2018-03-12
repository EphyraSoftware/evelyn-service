package org.evelyn.services.user.messaging.api;

import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;

public interface UserMessaging {
    String USER_CREATED_QUEUE_NAME = "user-created";

    void sendUserCreated(UserCreatedMessage userCreatedMessage);
}
