package org.evelyn.services.group.api.message;

import java.util.List;

public class GroupMessage {
    private String name;
    private List<GroupUserMessage> users;

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
