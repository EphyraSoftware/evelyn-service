package org.evelyn.services.user.data.mongo;

import org.evelyn.services.user.api.Profile;
import org.evelyn.services.user.data.api.ProfileDataService;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableMongoRepositories(basePackages = "org.evelyn.services.user.data.mongo")
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

        return profile;
    }
}
