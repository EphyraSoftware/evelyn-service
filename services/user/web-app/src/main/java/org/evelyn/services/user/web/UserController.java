package org.evelyn.services.user.web;

import org.evelyn.services.user.api.UserService;
import org.evelyn.services.user.api.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@RestController
@SpringBootApplication
@ComponentScan("org.evelyn.services.user")
public class UserController {
    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserMessage> getUsers() {
        return userService.getUsers();
    }
}
