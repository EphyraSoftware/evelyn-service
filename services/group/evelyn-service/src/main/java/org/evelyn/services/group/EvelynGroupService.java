package org.evelyn.services.group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.evelyn.services.group.api.message.GroupUserMessage;
import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupMessage;
import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;

@Service
public class EvelynGroupService implements GroupService {
    @Autowired
    private GroupDataService groupDataService;

    @Autowired
    private UserService userService;

    @Override
    public List<GroupMessage> getGroups() {
        List<Group> groups = groupDataService.getGroups();

        // Do some work.

        return groups.stream().map(group -> {
            GroupMessage groupMessage = new GroupMessage();
            groupMessage.setUsers(new ArrayList<>());
            for (String userId : group.getUserList()) {
                UserMessage user = userService.getUser(userId);

                GroupUserMessage groupUserMessage = new GroupUserMessage();
                groupUserMessage.setId(user.getId());
                groupUserMessage.setEmail(user.getEmail());
                groupMessage.getUsers().add(groupUserMessage);
            }
            groupMessage.setName(group.getName());

            return groupMessage;
        }).collect(Collectors.toList());
    }
}
