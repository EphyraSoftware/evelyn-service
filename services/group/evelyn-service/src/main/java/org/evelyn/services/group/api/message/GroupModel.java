package org.evelyn.services.group.api.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupModel implements Serializable {
    private String groupId;
    private String name;
    private List<GroupMemberModel> users;
}
