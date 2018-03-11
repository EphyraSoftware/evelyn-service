package org.evelyn.services.group.web;

import org.evelyn.services.group.api.GroupService;
import org.evelyn.services.user.api.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@SpringBootApplication
@ComponentScan("org.evelyn.services.group")
public class WebApp {
    @Value("${services.user.url}")
    private String userServiceUrl;

    @Bean(name = "/groupServiceExporter")
    public HttpInvokerServiceExporter groupServiceExporter(GroupService groupService) {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(groupService);
        exporter.setServiceInterface(GroupService.class);
        return exporter;
    }

    @Bean(name = "userServiceInvoker")
    public HttpInvokerProxyFactoryBean userServiceInvoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl(userServiceUrl);
        invoker.setServiceInterface(UserService.class);
        return invoker;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
