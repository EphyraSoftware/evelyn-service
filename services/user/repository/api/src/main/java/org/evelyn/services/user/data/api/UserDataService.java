package org.evelyn.services.user.data.api;

import java.util.List;

import org.evelyn.services.user.data.api.model.User;

public interface UserDataService {
    public List<User> getUsers();
}
