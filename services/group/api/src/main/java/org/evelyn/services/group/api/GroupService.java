package org.evelyn.services.group.api;

import java.util.List;

import org.evelyn.services.group.api.message.GroupMessage;

public interface GroupService {
    List<GroupMessage> getGroups();
}
