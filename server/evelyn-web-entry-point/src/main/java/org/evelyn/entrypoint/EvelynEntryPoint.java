package org.evelyn.entrypoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class EvelynEntryPoint {
    public static void main(String[] args) {
        SpringApplication.run(EvelynEntryPoint.class, args);
    }
}
