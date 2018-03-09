package org.evelyn.services.group.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.evelyn.services.group")
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
