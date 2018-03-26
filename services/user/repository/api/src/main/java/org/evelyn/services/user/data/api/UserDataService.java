package org.evelyn.services.user.data.api;

import org.evelyn.services.user.data.api.model.User;
import org.evelyn.services.user.data.api.model.UserRegistration;

public interface UserDataService {
    void saveUserRegistration(UserRegistration userRegistration);
    UserRegistration lookupRegistration(String confirmKey);
    void saveUser(User user);
    User getUserByEmail(String email);
    User getUser(String userId);
}
