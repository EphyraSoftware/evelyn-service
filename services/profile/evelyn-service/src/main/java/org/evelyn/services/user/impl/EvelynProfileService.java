package org.evelyn.services.user.impl;

import org.evelyn.services.user.api.Profile;
import org.evelyn.services.user.api.ProfileService;
import org.evelyn.services.user.data.api.ProfileDataService;
import org.evelyn.services.user.messaging.api.UserMessaging;
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
}
