package org.evelyn.services.group.data.mysql;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;

@Service
public class MySqlGroupData implements GroupDataService {
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();

        Group fooGroup = new Group();
        fooGroup.setName("foo-group-mysql");
        groups.add(fooGroup);

        Group barGroup = new Group();
        barGroup.setName("bar-group-mysql");
        groups.add(barGroup);

        return groups;
    }
}
