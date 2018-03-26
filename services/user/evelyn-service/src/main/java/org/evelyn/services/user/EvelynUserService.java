package org.evelyn.services.user;

import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.ConfirmRegistrationMessage;
import org.evelyn.services.user.api.message.SignInMessage;
import org.evelyn.services.user.api.message.UserMessage;
import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;
import org.evelyn.services.user.data.api.model.UserRegistration;
import org.evelyn.services.user.messaging.api.UserMessaging;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class EvelynUserService implements UserService {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserMessaging userMessaging;

    @Override
    public void registerUser(UserMessage userMessage) {
        UserRegistration userRegistration = new UserRegistration();
        // Provided fields.
        // TODO check incoming
        userRegistration.setEmail(userMessage.email);
        userRegistration.setUserHandle(userMessage.handle);
        userRegistration.setPassword(userMessage.password);
        // Generated fields.
        userRegistration.setExpiry(new Timestamp(new Date().getTime()).getTime() + 1000 * 24 * 60 * 60);
        userRegistration.setConfirmKey(
            new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()))
        );
        userDataService.saveUserRegistration(userRegistration);

        UserCreatedMessage userCreatedMessage = new UserCreatedMessage();
        userCreatedMessage.email = userMessage.email;
        userCreatedMessage.confirmKey = userRegistration.getConfirmKey();
        userMessaging.sendUserCreated(userCreatedMessage);
    }

    @Override
    public UserMessage confirmRegistration(ConfirmRegistrationMessage confirmRegistrationMessage) {
        UserRegistration userRegistration = userDataService.lookupRegistration(confirmRegistrationMessage.confirmKey);
        if (userRegistration == null) {
            // No match found in the database for this confirm key.
            // May already have been confirmed or may be somebody trying to guess a confirm key (probably won't help you!).
            return null;
        }

        Long now = new Timestamp(new Date().getTime()).getTime();
        if (now.compareTo(userRegistration.getExpiry()) >= 0) {
            // The expiry has passed, can't confirm.
            // TODO remove the registration record from the database.
            return null;
        }

        if (!confirmRegistrationMessage.email.equals(userRegistration.getEmail()) || !confirmRegistrationMessage.password.equals(userRegistration.getPassword())) {
            // Require a valid sign in with the confirm.
            // TODO possibly should remove registration record from the database if the login is incorrect? Would be annoying for the user but...
            return null;
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(userRegistration.getPassword());
        user.setHandle(userRegistration.getUserHandle());
        user.setDateCreated(new Date());
        userDataService.saveUser(user);

        UserMessage userMessage = new UserMessage();
        userMessage.email = user.getEmail();
        userMessage.handle = user.getHandle();
        return userMessage;
    }

    @Override
    public UserMessage signIn(SignInMessage signInMessage) {
        User user = userDataService.getUserByEmail(signInMessage.email);
        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(signInMessage.password)) {
            return null;
        }

        // TODO token.

        UserMessage userMessage = new UserMessage();
        userMessage.handle = user.getHandle();
        userMessage.email = user.getEmail();
        return userMessage;
    }

    @Override
    public UserMessage getUser(String userId) {
        User user = userDataService.getUser(userId);

        UserMessage userMessage = new UserMessage();
        userMessage.email = user.getEmail();
        userMessage.id = user.getId();

        return userMessage;
    }
}
