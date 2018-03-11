package org.evelyn.services.user.data.api;

import org.evelyn.services.user.data.api.model.User;

public interface UserDataService {
    void createUser(User user);
    User getUser(String userId);
}
