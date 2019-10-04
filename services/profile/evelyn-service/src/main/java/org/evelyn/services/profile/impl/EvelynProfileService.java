package org.evelyn.services.profile.impl;

import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.api.ProfileService;
import org.evelyn.services.profile.data.api.ProfileDataService;
import org.evelyn.services.profile.data.mongo.ProfileDocument;
import org.evelyn.services.profile.messaging.api.UserMessaging;
import org.springframework.stereotype.Service;

@Service
public class EvelynProfileService implements ProfileService {
  private ProfileDataService profileDataService;

  private final UserMessaging userMessaging;

  public EvelynProfileService(UserMessaging userMessaging, ProfileDataService profileDataService) {
    this.userMessaging = userMessaging;
    this.profileDataService = profileDataService;
  }

  @Override
  public Profile getProfile(String principalName) {
      Profile profile = profileDataService.getOrCreateProfile(principalName);

      if (profile.getNewRegistration()) {
          // Send welcome message!
      }

      return profile;
  }

  @Override
  public Profile updateProfile(String name, Profile profile) {
    ProfileDocument profileDocument = profileDataService.getProfile(profile.getProfileId(), name);

    profileDocument.nickname = profile.getNickname();

    profileDataService.updateProfile(profileDocument);

    return profile;
  }
}
