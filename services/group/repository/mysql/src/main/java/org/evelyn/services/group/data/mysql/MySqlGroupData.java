package org.evelyn.services.group.data.mysql;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;

@Service
public class MySqlGroupData implements GroupDataService {
    @Override
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();

        Group fooGroup = new Group();
        fooGroup.setName("foo-group");
        fooGroup.setUserList(new ArrayList<>());
        fooGroup.getUserList().add("asdlfkj9898fslkdfajsdf98");
        fooGroup.getUserList().add("sdfsfd98sflkjdsf09fsdf89");
        groups.add(fooGroup);

        Group barGroup = new Group();
        barGroup.setName("bar-group");
        barGroup.setUserList(new ArrayList<>());
        barGroup.getUserList().add("asdf098sfolkj34dlkj23409");
        groups.add(barGroup);

        return groups;
    }
}
