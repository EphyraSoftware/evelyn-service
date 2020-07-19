package org.evelyn.services.task.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
  @JsonProperty("access_token")
  private String authToken;

  private String error;

  @JsonProperty("error_description")
  private String errorDescription;
}
