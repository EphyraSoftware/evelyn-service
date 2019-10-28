package org.evelyn.services.group.data.api.model;

import lombok.Data;

@Data
public class GroupMember {
  private String profileId;
  private GroupRole groupRole;
}
