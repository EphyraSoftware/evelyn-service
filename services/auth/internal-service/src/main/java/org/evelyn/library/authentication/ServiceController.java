package org.evelyn.library.authentication;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SpringBootApplication
@ComponentScan("org.evelyn.services.auth")
@CrossOrigin("*")
public class ServiceController {
  @Bean
  public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  public static void main(String[] args) {
    SpringApplication.run(ServiceController.class, args);
  }

  @GetMapping("/test")
  @ResponseBody
  public TestModel2 root(final Principal principal) {
    System.out.println(principal.getName());

    TestModel2 testModel = new TestModel2();
    testModel.value2 = "hello service world";
    return testModel;
  }
}

@JsonSerialize
class TestModel2 {
  public String value2;
}
