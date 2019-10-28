package org.evelyn.services.group.data.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private String groupId;
    private String name;
    private List<GroupMember> userList;
}
