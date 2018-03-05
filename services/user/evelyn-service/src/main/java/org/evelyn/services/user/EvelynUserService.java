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

    public List<UserMessage> getUsers() {
        List<User> users = userDataService.getUsers();

        // Do some work.
        
        return users.stream().map(user -> {
            UserMessage userMessage = new UserMessage();
            userMessage.setName(user.getName());

            return userMessage;
        }).collect(Collectors.toList());
    }
}
