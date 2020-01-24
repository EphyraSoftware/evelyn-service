package org.evelyn.services.profile.api;

public interface ProfileService {
  Profile getProfile(AuthenticationInfo authenticationInfo);

  Profile updateProfile(String id, Profile profile);
}
