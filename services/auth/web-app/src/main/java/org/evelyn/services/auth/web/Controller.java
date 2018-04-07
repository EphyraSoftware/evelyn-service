package org.evelyn.services.auth.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@SpringBootApplication
@ComponentScan("org.evelyn.services.auth")
public class Controller {
    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test")
    @ResponseBody
    public TestModel root(final Principal principal) {
        System.out.println(principal.getName());

        TestModel testModel = new TestModel();
        testModel.value = "hello world";
        return testModel;
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "success";
    }
}

@JsonSerialize
class TestModel {
    public String value;
}
