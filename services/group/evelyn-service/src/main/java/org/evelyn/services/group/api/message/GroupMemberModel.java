package org.evelyn.services.group.api.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupMemberModel implements Serializable {
    private String id;
    private GroupRoleModel groupRoleModel;
    private String email;

}
