package org.evelyn.services.group.api.message;

import java.io.Serializable;
import java.util.List;

public class GroupMessage implements Serializable {
    private String groupId;
    private String name;
    private List<GroupUserMessage> users;

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

    public List<GroupUserMessage> getUsers() {
        return users;
    }

    public void setUsers(List<GroupUserMessage> users) {
        this.users = users;
    }
}
