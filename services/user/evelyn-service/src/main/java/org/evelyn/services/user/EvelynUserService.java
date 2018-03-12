package org.evelyn.services.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.evelyn.services.user.messaging.api.UserMessaging;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.UserMessage;
import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;

@Service
public class EvelynUserService implements UserService {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserMessaging userMessaging;

    @Override
    public UserMessage createUser(UserMessage userMessage) {
        userMessage.setId(UUID.randomUUID().toString());

        User user = new User();
        user.setId(userMessage.getId());
        user.setEmail(userMessage.getEmail());
        userDataService.createUser(user);

        UserCreatedMessage userCreatedMessage = new UserCreatedMessage();
        userCreatedMessage.email = userMessage.getEmail();
        userMessaging.sendUserCreated(userCreatedMessage);

        return userMessage;
    }

    @Override
    public UserMessage getUser(String userId) {
        User user = userDataService.getUser(userId);

        UserMessage userMessage = new UserMessage();
        userMessage.setEmail(user.getEmail());
        userMessage.setId(user.getId());
        return userMessage;
    }
}
