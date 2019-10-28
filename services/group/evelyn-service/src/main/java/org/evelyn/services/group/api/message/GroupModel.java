package org.evelyn.services.group.api.message;

import java.io.Serializable;
import java.util.List;

public class GroupModel implements Serializable {
    private String groupId;
    private String name;
    private List<GroupMemberModel> users;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMemberModel> getProfiles() {
        return users;
    }

    public void setUsers(List<GroupMemberModel> users) {
        this.users = users;
    }
}
