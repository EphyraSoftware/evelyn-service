package org.evelyn.services.user.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<ProfileDocument, String> {
    ProfileDocument findByPrincipalName(String principalName);
}
