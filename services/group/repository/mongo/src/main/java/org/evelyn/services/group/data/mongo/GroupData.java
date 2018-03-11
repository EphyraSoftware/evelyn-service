package org.evelyn.services.group.data.mongo;

import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableMongoRepositories(basePackages = "org.evelyn.services.group.data.mongo")
public class GroupData implements GroupDataService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void createGroup(Group group) {
        org.evelyn.services.group.data.mongo.Group groupDocument = new org.evelyn.services.group.data.mongo.Group();
        groupDocument.groupId = group.getGroupId();
        groupDocument.name = group.getName();
        groupDocument.userIds = group.getUserList();
        groupRepository.insert(groupDocument);
    }

    @Override
    public List<Group> getGroups() {
        List<org.evelyn.services.group.data.mongo.Group> groups = groupRepository.findAll();
        return groups.stream().map(groupDocument -> {
            Group group = new Group();
            group.setGroupId(groupDocument.groupId);
            group.setName(groupDocument.name);
            group.setUserList(groupDocument.userIds);

            return group;
        }).collect(Collectors.toList());
    }
}
