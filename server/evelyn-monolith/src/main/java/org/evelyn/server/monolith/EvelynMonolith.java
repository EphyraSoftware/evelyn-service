package org.evelyn.server.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"org.evelyn.services"})
public class EvelynMonolith {
    public static void main(String[] args) {
        SpringApplication.run(EvelynMonolith.class, args);
    }

    @RequestMapping(value = "/tester", method = RequestMethod.GET)
    public String getTester() {
        return "Testing, Testing!";
    }
}
