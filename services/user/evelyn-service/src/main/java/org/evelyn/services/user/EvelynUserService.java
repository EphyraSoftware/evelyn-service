package org.evelyn.services.user;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.evelyn.services.user.api.message.ConfirmRegistrationMessage;
import org.evelyn.services.user.data.api.model.UserRegistration;
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
    public void registerUser(UserMessage userMessage) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setEmail(userMessage.getEmail());
        userRegistration.setUserHandle(userMessage.getHandle());
        userRegistration.setConfirmKey(
            new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()))
        );
        userDataService.saveUserRegistration(userRegistration);

        UserCreatedMessage userCreatedMessage = new UserCreatedMessage();
        userCreatedMessage.email = userMessage.getEmail();
        userMessaging.sendUserCreated(userCreatedMessage);
    }

    @Override
    public UserMessage confirmRegistration(ConfirmRegistrationMessage confirmRegistrationMessage) {
        UserRegistration userRegistration = userDataService.lookupRegistration(confirmRegistrationMessage.getConfirmKey());
        if (userRegistration == null) {
            // No match found in the database for this confirm key.
            // May already have been confirmed or may be somebody trying to guess a confirm key (probably won't help you!).
            return null;
        }

        Date now = new Date();
        if (now.compareTo(userRegistration.getExpiry()) >= 0) {
            // The expiry has passed, can't confirm.
            // TODO remove the registration record from the database.
            return null;
        }

        User user = new User();
        user.setEmail(userRegistration.getEmail());
        user.setId(UUID.randomUUID().toString());
        user.setDateCreated(new Date());
        user.setHandle(userRegistration.getUserHandle());
        userDataService.saveUser(user);

        UserMessage userMessage = new UserMessage();
        userMessage.setEmail(user.getEmail());
        userMessage.setHandle(user.getHandle());
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
