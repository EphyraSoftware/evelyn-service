package org.evelyn.services.user.data.mongo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;

@Service
public class MongoUserData implements UserDataService {
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User fooUser = new User();
        fooUser.setName("foo-mongo@evelyn.org");
        users.add(fooUser);

        User barUser = new User();
        barUser.setName("bar-mongo@evelyn.org");
        users.add(barUser);

        return users;
    }
}
