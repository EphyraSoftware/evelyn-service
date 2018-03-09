package org.evelyn.services.group;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupMessage;
import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;

@Service
public class EvelynGroupService implements GroupService {
    @Autowired
    private GroupDataService userDataService;

    @Override
    public List<GroupMessage> getGroups() {
        List<Group> groups = userDataService.getGroups();

        // Do some work.
        
        return groups.stream().map(group -> {
            GroupMessage groupMessage = new GroupMessage();
            groupMessage.setName(group.getName());

            return groupMessage;
        }).collect(Collectors.toList());
    }
}
