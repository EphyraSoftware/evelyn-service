package org.evelyn.services.group.api;

import java.util.List;

import org.evelyn.services.group.api.message.GroupMessage;

public interface GroupService {
    GroupMessage createGroup(GroupMessage groupMessage);
    List<GroupMessage> getGroups();
}
