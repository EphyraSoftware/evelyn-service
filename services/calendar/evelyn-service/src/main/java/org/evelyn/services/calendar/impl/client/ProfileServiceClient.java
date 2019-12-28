package org.evelyn.services.calendar.impl.client;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("calendarProfileServiceClient")
public class ProfileServiceClient {
  @Value("${org.evelyn.calendar.profile-service-url}")
  private String userServiceUrl;

  private final KeycloakRestTemplate restTemplate;

  public ProfileServiceClient(KeycloakRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseEntity<ProfileModel> getProfile(String userId) {
    return restTemplate.getForEntity(userServiceUrl + "/profiles", ProfileModel.class);
  }

  public ResponseEntity<ProfileModel> getCurrentProfile() {
    return restTemplate.getForEntity(userServiceUrl + "/profiles", ProfileModel.class);
  }
}
