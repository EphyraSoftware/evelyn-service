package org.evelyn.services.group.rest.api;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.group.api.message.GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupMessage> getGroups() {
        return groupService.getGroups();
    }
}
