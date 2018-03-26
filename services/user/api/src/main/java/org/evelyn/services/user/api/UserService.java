package org.evelyn.services.user.api;

import java.util.List;

import org.evelyn.services.user.api.message.ConfirmRegistrationMessage;
import org.evelyn.services.user.api.message.SignInMessage;
import org.evelyn.services.user.api.message.UserMessage;

public interface UserService {
    void registerUser(UserMessage userMessage);

    UserMessage confirmRegistration(ConfirmRegistrationMessage confirmRegistrationMessage);

    UserMessage signIn(SignInMessage signInMessage);

    UserMessage getUser(String userId);
}
