package org.evelyn.services.group.rest.api;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    @ResponseBody
    public GroupMessage getGroups(@RequestBody GroupMessage groupMessage) {
        return groupService.createGroup(groupMessage);
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupMessage> getGroups() {
        return groupService.getGroups();
    }
}
