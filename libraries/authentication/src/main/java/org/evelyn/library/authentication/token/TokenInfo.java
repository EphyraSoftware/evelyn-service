package org.evelyn.library.authentication.token;


public class TokenInfo {
  private final String email;
  private final String subject;

  public TokenInfo(String subject, String email) {
    this.subject = subject;
    this.email = email;
  }

  public String getSubject() {
    return subject;
  }

  public String getEmail() {
    return email;
  }
}
