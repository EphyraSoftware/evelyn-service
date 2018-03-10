package org.evelyn.services.user.api;

import java.util.List;

import org.evelyn.services.user.api.message.UserMessage;

public interface UserService {
    List<UserMessage> getUsers();

    UserMessage getUser(String userId);
}
