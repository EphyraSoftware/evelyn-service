package org.evelyn.services.user.data.mysql;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;

@Service
public class MySqlUserData implements UserDataService {
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(getUser("asdlfkj9898fslkdfajsdf98"));
        users.add(getUser("sdfsfd98sflkjdsf09fsdf89"));
        users.add(getUser("asdf098sfolkj34dlkj23409"));
        return users;
    }

    @Override
    public User getUser(String userId) {
        String email = null;
        switch (userId) {
            case "asdlfkj9898fslkdfajsdf98":
                email = "foo@evelyn.com";
                break;
            case "sdfsfd98sflkjdsf09fsdf89":
                email = "bar@evelyn.com";
                break;
            case "asdf098sfolkj34dlkj23409":
                email = "foobar@evelyn.com";
                break;
        }

        User user = new User();
        user.setEmail(email);
        user.setId(userId);

        return user;
    }
}
