package org.evelyn.server.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"org.evelyn.services", "org.evelyn.library.authentication"})
public class EvelynMonolith {
    public static void main(String[] args) {
        SpringApplication.run(EvelynMonolith.class, args);
    }
}
