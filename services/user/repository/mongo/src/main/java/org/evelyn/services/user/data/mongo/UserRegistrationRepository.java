package org.evelyn.services.user.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRegistrationRepository extends MongoRepository<UserRegistrationDocument, String> {
    public UserRegistrationDocument findByConfirmKey(String confirmKey);
}
