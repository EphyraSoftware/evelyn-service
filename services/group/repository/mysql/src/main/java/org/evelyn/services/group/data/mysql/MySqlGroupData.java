package org.evelyn.services.group.data.mysql;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;

@Service
public class MySqlGroupData implements GroupDataService {
    public List<Group> getUsers() {
        List<Group> users = new ArrayList<>();

        Group fooUser = new Group();
        fooUser.setName("foo-group-mysql");
        users.add(fooUser);

        Group barUser = new Group();
        barUser.setName("bar-group-mysql");
        users.add(barUser);

        return users;
    }
}
