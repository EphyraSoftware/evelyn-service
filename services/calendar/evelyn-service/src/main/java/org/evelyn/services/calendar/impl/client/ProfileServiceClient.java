package org.evelyn.services.calendar.impl.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("calendarProfileServiceClient")
public class ProfileServiceClient {
  @Value("${org.evelyn.calendar.profile-service-url}")
  private String userServiceUrl;

  private final RestTemplate restTemplate;

  public ProfileServiceClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ResponseEntity<ProfileModel> getProfile(String userId) {
    return restTemplate.getForEntity(userServiceUrl + "/profiles", ProfileModel.class);
  }

  public ResponseEntity<ProfileModel> getCurrentProfile() {
    return restTemplate.getForEntity(userServiceUrl + "/profiles", ProfileModel.class);
  }
}
