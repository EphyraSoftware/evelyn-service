package org.evelyn.services.user.rest.api;

import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.ConfirmRegistrationMessage;
import org.evelyn.services.user.api.message.SignInMessage;
import org.evelyn.services.user.api.message.UserMessage;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public void register(@RequestBody UserMessage userMessage) {
        userService.registerUser(userMessage);
    }

    @RequestMapping(value = "/users/confirm", method = RequestMethod.POST)
    @ResponseBody
    public UserMessage confirm(@RequestBody ConfirmRegistrationMessage confirmRegistrationMessage) {
        return userService.confirmRegistration(confirmRegistrationMessage);
    }

    @RequestMapping(value = "/users/signIn", method = RequestMethod.POST)
    @ResponseBody
    public UserMessage confirm(@RequestBody SignInMessage signInMessage) {
        return userService.signIn(signInMessage);
    }
}
