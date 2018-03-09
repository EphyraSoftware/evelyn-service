package org.evelyn.services.group.data.mongo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;

@Service
public class GroupUserData implements GroupDataService {
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();

        Group fooGroup = new Group();
        fooGroup.setName("foo-group-mongo");
        groups.add(fooGroup);

        Group barGroup = new Group();
        barGroup.setName("bar-group-mongo");
        groups.add(barGroup);

        return groups;
    }
}
