package org.evelyn.services.profile.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<ProfileDocument, String> {
    ProfileDocument findByPrincipalName(String principalName);

    ProfileDocument findByIdAndPrincipalName(String profileId, String principalName);
}
