package org.evelyn.services.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SpringBootApplication
@ComponentScan(value = {"org.evelyn.services.auth", "org.evelyn.library.authentication"})
@CrossOrigin("*")
public class Controller {
    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

    @Autowired
    private KeycloakRestTemplate template;

    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/test")
    @ResponseBody
    public TestModel root(final Principal principal) {
        System.out.println(principal.getName());

        ResponseEntity<TestModel2> testModel2 = template.getForEntity("http://localhost:8086/test", TestModel2.class);

        TestModel testModel = new TestModel();
        testModel.value = testModel2.getBody().value2;
        return testModel;
    }
}

@JsonSerialize
class TestModel {
    public String value;
}

@JsonSerialize
class TestModel2 {
    public String value2;
}

