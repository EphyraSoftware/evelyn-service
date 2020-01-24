package org.evelyn.services.profile.api;

public class AuthenticationInfo {
  private String subject;
  private String email;

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSubject() {
    return subject;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
