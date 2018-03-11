package org.evelyn.services.user.data.mongo;

import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableMongoRepositories(basePackages = "org.evelyn.services.user.data.mongo")
public class MongoUserData implements UserDataService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        org.evelyn.services.user.data.mongo.User userDocument = new org.evelyn.services.user.data.mongo.User();
        userDocument.userId = user.getId();
        userDocument.email = user.getEmail();

        userRepository.insert(userDocument);
    }

    @Override
    public User getUser(String userId) {
        org.evelyn.services.user.data.mongo.User byUserId = userRepository.findByUserId(userId);
        if (byUserId == null) {
            return null;
        }

        User user = new User();
        user.setId(byUserId.userId);
        user.setEmail(byUserId.email);
        return user;
    }
}
