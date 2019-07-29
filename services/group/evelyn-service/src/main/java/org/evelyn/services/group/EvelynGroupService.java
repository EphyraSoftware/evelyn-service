package org.evelyn.services.group;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupMessage;
import org.evelyn.services.group.api.message.GroupUserMessage;
import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;
import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.UserMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EvelynGroupService implements GroupService {
    private final GroupDataService groupDataService;

    private final UserService userService;

    public EvelynGroupService(GroupDataService groupDataService, @Qualifier("evelynUserService") UserService userService) {
        this.groupDataService = groupDataService;
        this.userService = userService;
    }

    @Override
    public GroupMessage createGroup(GroupMessage groupMessage) {
        groupMessage.setGroupId(UUID.randomUUID().toString());

        Group group = new Group();
        group.setGroupId(groupMessage.getGroupId());
        group.setName(groupMessage.getName());
        group.setUserList(groupMessage.getUsers().stream().map(GroupUserMessage::getId).collect(Collectors.toList()));
        groupDataService.createGroup(group);

        groupMessage.getUsers().forEach(userMessage -> {
            UserMessage user = userService.getUser(userMessage.getId());
            userMessage.setEmail(user.email);
        });

        return groupMessage;
    }

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
                groupUserMessage.setId(user.id);
                groupUserMessage.setEmail(user.email);
                groupMessage.getUsers().add(groupUserMessage);
            }
            groupMessage.setName(group.getName());
            groupMessage.setGroupId(group.getGroupId());

            return groupMessage;
        }).collect(Collectors.toList());
    }
}
