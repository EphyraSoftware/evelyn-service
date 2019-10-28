package org.evelyn.services.group.api;

import org.evelyn.services.group.api.message.GroupModel;

import java.util.List;

public interface GroupService {
    GroupModel createGroup(GroupModel groupModel);
    List<GroupModel> getGroups();
}
