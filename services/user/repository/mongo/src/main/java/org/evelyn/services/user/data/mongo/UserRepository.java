package org.evelyn.services.user.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    UserDocument findByUserId(String userId);
}
