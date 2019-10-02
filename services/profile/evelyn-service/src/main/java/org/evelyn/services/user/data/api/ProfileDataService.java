package org.evelyn.services.user.data.api;

import org.evelyn.services.user.api.Profile;

public interface ProfileDataService {
    Profile getOrCreateProfile(String principalName);
}
