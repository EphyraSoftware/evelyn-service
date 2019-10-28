package org.evelyn.services.group.data.api;

import org.evelyn.services.group.data.api.model.Group;

import java.util.List;

public interface GroupDataService {
    void createGroup(Group group);
    List<Group> getGroups();
}
