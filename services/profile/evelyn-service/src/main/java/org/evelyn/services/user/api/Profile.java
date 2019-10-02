package org.evelyn.services.user.api;

public class Profile {
  private boolean newRegistration = false;

  public void setNewRegistration(boolean newRegistration) {
    this.newRegistration = newRegistration;
  }

  public boolean getNewRegistration() {
    return newRegistration;
  }
}
