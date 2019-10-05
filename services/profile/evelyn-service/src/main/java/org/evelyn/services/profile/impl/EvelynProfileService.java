package org.evelyn.services.profile.impl;

import freemarker.template.TemplateException;
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
  public Profile getProfile(String principalName) {
      Profile profile = profileDataService.getOrCreateProfile(principalName);

      if (profile.getNewRegistration()) {
        try {
          registrationMailSender.sendRegistrationMessage(profile);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (TemplateException e) {
          e.printStackTrace();
        }
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
