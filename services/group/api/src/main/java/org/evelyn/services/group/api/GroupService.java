package org.evelyn.services.group.api;

import org.evelyn.services.group.api.message.GroupMessage;

import java.util.List;

public interface GroupService {
    GroupMessage createGroup(GroupMessage groupMessage);
    List<GroupMessage> getGroups();
}
