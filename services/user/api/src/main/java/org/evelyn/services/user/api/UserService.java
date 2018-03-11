package org.evelyn.services.user.api;

import java.util.List;

import org.evelyn.services.user.api.message.UserMessage;

public interface UserService {
    UserMessage createUser(UserMessage userMessage);

    UserMessage getUser(String userId);
}
