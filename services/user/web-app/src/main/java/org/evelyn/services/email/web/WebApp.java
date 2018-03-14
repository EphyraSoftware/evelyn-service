package org.evelyn.services.email.web;

import org.evelyn.services.user.api.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@SpringBootApplication
@ComponentScan("org.evelyn.services.user")
public class WebApp {
    @Bean(name = "/userServiceExporter")
    public HttpInvokerServiceExporter userServiceExporter(UserService userService) {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(userService);
        exporter.setServiceInterface(UserService.class);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
