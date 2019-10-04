package org.evelyn.services.profile.api;

public class Profile {
  private String profileId;
  private boolean newRegistration = false;

  private String nickname;

  public void setNewRegistration(boolean newRegistration) {
    this.newRegistration = newRegistration;
  }

  public boolean getNewRegistration() {
    return newRegistration;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getProfileId() {
    return profileId;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }
}
