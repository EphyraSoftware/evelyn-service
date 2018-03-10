package org.evelyn.services.user;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UserMessage> getUsers() {
        List<User> users = userDataService.getUsers();

        // Do some work.

        return users.stream().map(user -> {
            UserMessage userMessage = new UserMessage();
            userMessage.setId(user.getId());
            userMessage.setEmail(user.getEmail());

            return userMessage;
        }).collect(Collectors.toList());
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
