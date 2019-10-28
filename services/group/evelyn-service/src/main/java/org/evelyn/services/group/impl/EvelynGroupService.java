package org.evelyn.services.group.impl;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupMemberModel;
import org.evelyn.services.group.api.message.GroupModel;
import org.evelyn.services.group.api.message.GroupRoleModel;
import org.evelyn.services.group.data.api.GroupDataService;
import org.evelyn.services.group.data.api.model.Group;
import org.evelyn.services.group.data.api.model.GroupMember;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.evelyn.services.group.data.api.model.GroupRole.Owner;

@Service
public class EvelynGroupService implements GroupService {
  private final GroupDataService groupDataService;

  private final ProfileServiceClient profileServiceClient;

  public EvelynGroupService(GroupDataService groupDataService, ProfileServiceClient profileServiceClient) {
    this.groupDataService = groupDataService;
    this.profileServiceClient = profileServiceClient;
  }

  @Override
  public GroupModel createGroup(GroupModel groupModel) {
    ResponseEntity<ProfileModel> currentProfile = profileServiceClient.getCurrentProfile();
    if (currentProfile.getBody() == null) {
      throw new RuntimeException("Failed to get current profile");
    }

    Group group = new Group();
    group.setGroupId(UUID.randomUUID().toString());
    group.setName(groupModel.getName());

    var groupMember = new GroupMember();
    groupMember.setProfileId(currentProfile.getBody().getProfileId());
    groupMember.setGroupRole(Owner);
    group.getUserList().add(groupMember);

    groupDataService.createGroup(group);

    groupModel.setUsers(Collections.singletonList(mapGroupMember(groupMember)));
    return groupModel;
  }

  private GroupMemberModel mapGroupMember(GroupMember groupMember) {
    var groupMemberModel = new GroupMemberModel();
    groupMemberModel.setId(groupMember.getProfileId());
    switch (groupMember.getGroupRole()) {
      case Owner:
        groupMemberModel.setGroupRoleModel(GroupRoleModel.Owner);
        break;
      case Member:
        groupMemberModel.setGroupRoleModel(GroupRoleModel.Member);
    }

    return groupMemberModel;
  }

  @Override
  public List<GroupModel> getGroups() {
    List<Group> groups = groupDataService.getGroups();

    return groups.stream().map(group -> {
      GroupModel groupModel = new GroupModel();
      groupModel.setUsers(new ArrayList<>());
      for (var groupMember : group.getUserList()) {
        var profileResponse = profileServiceClient.getProfile(groupMember.getProfileId());

        ProfileModel profileModel = profileResponse.getBody();
        if (profileModel == null) {
          continue;
        }

        GroupMemberModel groupMemberModel = new GroupMemberModel();
        groupMemberModel.setId(profileModel.getProfileId());
        groupMemberModel.setEmail(profileModel.getEmail());
        groupModel.getProfiles().add(groupMemberModel);
      }
      groupModel.setName(group.getName());
      groupModel.setGroupId(group.getGroupId());

      return groupModel;
    }).collect(Collectors.toList());
  }
}
