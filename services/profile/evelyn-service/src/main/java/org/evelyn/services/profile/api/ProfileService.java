package org.evelyn.services.profile.api;

public interface ProfileService {
  Profile getProfile(String principalName);

  Profile updateProfile(String name, Profile profile);
}
