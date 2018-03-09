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
        List<Group> users = userDataService.getUsers();

        // Do some work.
        
        return users.stream().map(user -> {
            GroupMessage userMessage = new GroupMessage();
            userMessage.setName(user.getName());

            return userMessage;
        }).collect(Collectors.toList());
    }
}
