package org.evelyn.services.profile.messaging.api;

import org.evelyn.services.profile.messaging.api.model.UserCreatedMessage;

public interface UserMessaging {
    String USER_CREATED_QUEUE_NAME = "user-created";

    void sendUserCreated(UserCreatedMessage userCreatedMessage);
}
