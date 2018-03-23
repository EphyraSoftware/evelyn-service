package org.evelyn.services.user.rest.api;

import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.ConfirmRegistrationMessage;
import org.evelyn.services.user.api.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public void getUsers(@RequestBody UserMessage userMessage) {
        userService.registerUser(userMessage);
    }

    @RequestMapping(value = "/users/confirm", method = RequestMethod.POST)
    @ResponseBody
    public UserMessage getUsers(@RequestBody ConfirmRegistrationMessage confirmRegistrationMessage) {
        return userService.confirmRegistration(confirmRegistrationMessage);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public UserMessage getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }
}
