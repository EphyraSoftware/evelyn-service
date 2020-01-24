package org.evelyn.services.profile.impl;

import freemarker.template.TemplateException;
import org.evelyn.services.profile.api.AuthenticationInfo;
import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.api.ProfileService;
import org.evelyn.services.profile.data.api.ProfileDataService;
import org.evelyn.services.profile.data.mongo.ProfileDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EvelynProfileService implements ProfileService {
  private final RegistrationMailSender registrationMailSender;

  private ProfileDataService profileDataService;

  public EvelynProfileService(ProfileDataService profileDataService, RegistrationMailSender registrationMailSender) {
    this.profileDataService = profileDataService;
    this.registrationMailSender = registrationMailSender;
  }

  @Override
  public Profile getProfile(AuthenticationInfo authenticationInfo) {
    Profile profile = profileDataService.getOrCreateProfile(authenticationInfo.getSubject(), authenticationInfo.getEmail());

    if (profile.getNewRegistration()) {
      try {
        registrationMailSender.sendRegistrationMessage(profile);
      } catch (IOException | TemplateException e) {
        e.printStackTrace();
      }
    }

    return profile;
  }

  @Override
  public Profile updateProfile(String id, Profile profile) {
    ProfileDocument profileDocument = profileDataService.getProfile(id);

    profileDocument.nickname = profile.getNickname();

    profileDataService.updateProfile(profileDocument);

    return profile;
  }
}
