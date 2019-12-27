package org.evelyn.services.task.impl;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("taskProfileServiceClient")
public class ProfileServiceClient {
    @Value("${org.evelyn.task.profile-service-url}")
    private String profileServiceUrl;

    private final KeycloakRestTemplate restTemplate;

    public ProfileServiceClient(KeycloakRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<ProfileModel> getCurrentProfile() {
        return restTemplate.getForEntity(profileServiceUrl + "/profiles", ProfileModel.class);
    }
}
