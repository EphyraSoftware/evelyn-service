package org.evelyn.services.user.data.mongo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;

@Service
public class MySqlUserData implements UserDataService {
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User fooUser = new User();
        fooUser.setName("foo-mysql@evelyn.org");
        users.add(fooUser);

        User barUser = new User();
        barUser.setName("bar-mysql@evelyn.org");
        users.add(barUser);

        return users;
    }
}
