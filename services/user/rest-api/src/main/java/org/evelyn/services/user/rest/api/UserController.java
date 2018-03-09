package org.evelyn.services.user.rest.api;

import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserMessage> getUsers() {
        return userService.getUsers();
    }
}
