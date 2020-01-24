package org.evelyn.services.profile.data.api;

import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.data.mongo.ProfileDocument;

public interface ProfileDataService {
    Profile getOrCreateProfile(String id, String email);

    ProfileDocument getProfile(String profileId);

    void updateProfile(ProfileDocument profileDocument);
}
