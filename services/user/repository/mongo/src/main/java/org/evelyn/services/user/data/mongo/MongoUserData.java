package org.evelyn.services.user.data.mongo;

import org.evelyn.services.user.data.api.UserDataService;
import org.evelyn.services.user.data.api.model.User;
import org.evelyn.services.user.data.api.model.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableMongoRepositories(basePackages = "org.evelyn.services.user.data.mongo")
public class MongoUserData implements UserDataService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRegistrationRepository registrationRepository;

    @Override
    public void saveUserRegistration(UserRegistration userRegistration) {
        UserRegistrationDocument document = new UserRegistrationDocument();
        document.expiry = userRegistration.getExpiry();
        document.email = userRegistration.getEmail();
        document.confirmKey = userRegistration.getConfirmKey();
        document.userHandle = userRegistration.getUserHandle();
        document.password = userRegistration.getPassword();

        registrationRepository.insert(document);
    }

    @Override
    public UserRegistration lookupRegistration(String confirmKey) {
        UserRegistrationDocument userRegistrationDocument = registrationRepository.findByConfirmKey(confirmKey);
        if (userRegistrationDocument == null) {
            return null;
        }

        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setEmail(userRegistrationDocument.email);
        userRegistration.setExpiry(userRegistrationDocument.expiry);
        userRegistration.setUserHandle(userRegistrationDocument.userHandle);
        userRegistration.setPassword(userRegistrationDocument.password);
        return userRegistration;
    }

    @Override
    public void saveUser(User user) {
        UserDocument userDocument = new UserDocument();
        userDocument.dateCreated = user.getDateCreated();
        userDocument.email = user.getEmail();
        userDocument.handle = user.getHandle();
        userDocument.password = user.getPassword();

        userRepository.insert(userDocument);
    }

    @Override
    public User getUserByEmail(String email) {
        UserDocument userDocument = userRepository.findByEmail(email);
        if (userDocument == null) {
            return null;
        }

        User user = new User();
        user.setHandle(userDocument.handle);
        user.setEmail(userDocument.email);
        user.setPassword(userDocument.password);
        return user;
    }

    @Override
    public User getUser(String userId) {
        UserDocument byUserId = userRepository.findByUserId(userId);
        if (byUserId == null) {
            return null;
        }

        User user = new User();
        user.setId(byUserId.userId);
        user.setEmail(byUserId.email);
        return user;
    }
}
