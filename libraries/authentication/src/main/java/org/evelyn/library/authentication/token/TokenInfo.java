package org.evelyn.library.authentication.token;

import org.keycloak.representations.AccessToken;

public class TokenInfo {
  private final String email;
  private final String subject;

  public TokenInfo(AccessToken token) {
    subject = token.getSubject();
    email = token.getEmail();
  }

  public String getSubject() {
    return subject;
  }

  public String getEmail() {
    return email;
  }
}
