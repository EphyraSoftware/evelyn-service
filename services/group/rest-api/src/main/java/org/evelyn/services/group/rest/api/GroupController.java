package org.evelyn.services.group.rest.api;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    @ResponseBody
    public GroupModel getGroups(@RequestBody GroupModel groupModel) {
        return groupService.createGroup(groupModel);
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupModel> getGroups() {
        return groupService.getGroups();
    }
}
