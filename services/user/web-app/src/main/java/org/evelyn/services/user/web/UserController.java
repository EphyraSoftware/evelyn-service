package org.evelyn.services.user.web;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@SpringBootApplication
public class UserController {
    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }
}
