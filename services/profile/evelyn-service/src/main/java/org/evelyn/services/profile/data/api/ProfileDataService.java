package org.evelyn.services.profile.data.api;

import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.data.mongo.ProfileDocument;

public interface ProfileDataService {
    Profile getOrCreateProfile(String principalName);

    ProfileDocument getProfile(String profileId, String principalName);

    void updateProfile(ProfileDocument profileDocument);
}
