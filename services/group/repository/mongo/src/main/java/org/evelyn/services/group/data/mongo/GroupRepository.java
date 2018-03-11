package org.evelyn.services.group.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface GroupRepository extends MongoRepository<Group, String> {
}
