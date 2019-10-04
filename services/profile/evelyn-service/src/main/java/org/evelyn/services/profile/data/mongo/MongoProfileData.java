package org.evelyn.services.profile.data.mongo;

import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.data.api.ProfileDataService;
import org.springframework.stereotype.Service;

@Service
public class MongoProfileData implements ProfileDataService {
    private final ProfileRepository profileRepository;

    public MongoProfileData(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile getOrCreateProfile(String principalName) {
        Profile profile = new Profile();

        ProfileDocument profileDocument = profileRepository.findByPrincipalName(principalName);
        if (profileDocument == null) {
            var newProfile = new ProfileDocument();
            newProfile.principalName = principalName;
            profileDocument = profileRepository.insert(newProfile);

            profile.setNewRegistration(true);
        }

        // Map profile fields here.
        profile.setProfileId(profileDocument.id);
        profile.setNickname(profileDocument.nickname);

        return profile;
    }

    @Override
    public ProfileDocument getProfile(String profileId, String principalName) {
        return profileRepository.findByIdAndPrincipalName(profileId, principalName);
    }

    @Override
    public void updateProfile(ProfileDocument profileDocument) {
        profileRepository.save(profileDocument);
    }

}
