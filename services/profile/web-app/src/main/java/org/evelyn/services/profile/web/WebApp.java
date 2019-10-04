package org.evelyn.services.profile.web;

import org.evelyn.library.configuration.EnableRemoteConfigServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.evelyn.services.profile", "org.evelyn.library.authentication"})
@EnableRemoteConfigServer
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
