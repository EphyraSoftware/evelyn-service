package org.evelyn.services.user.api;

import java.util.List;

import org.evelyn.services.user.api.message.UserMessage;

public interface UserService {
    public List<UserMessage> getUsers();
}
