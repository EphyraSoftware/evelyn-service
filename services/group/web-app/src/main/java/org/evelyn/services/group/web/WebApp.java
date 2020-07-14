package org.evelyn.services.group.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@SpringBootApplication
@ComponentScan(basePackages = {"org.evelyn.services.group", "org.evelyn.library.authentication"})
public class WebApp {
    public static void main(String[] args) {
        configureTrustStore();

        SpringApplication.run(WebApp.class, args);
    }

    private static void configureTrustStore() {
        var trustStore = "/etc/evelyn/truststore.p12";
        var trustStoreFile = new File(trustStore);
        if (trustStoreFile.isFile() && trustStoreFile.canRead()) {
            System.out.println("Configuring truststore.");
            System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.trustStore", trustStore);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        }
    }

    @Bean
    public RestTemplate createRestTemplate() {
        var rest = new RestTemplate();
        rest.getInterceptors().add((request, body, execution) -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return execution.execute(request, body);
            }

            if (!(authentication.getCredentials() instanceof AbstractOAuth2Token)) {
                return execution.execute(request, body);
            }

            var token = (AbstractOAuth2Token) authentication.getCredentials();
            request.getHeaders().setBearerAuth(token.getTokenValue());
            return execution.execute(request, body);
        });
        return rest;
    }
}
