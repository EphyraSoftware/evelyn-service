package org.evelyn.services.profile.data.mongo;

import com.mongodb.client.result.UpdateResult;
import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.data.api.ProfileDataService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MongoProfileData implements ProfileDataService {
    private final MongoTemplate mongoTemplate;

    public MongoProfileData(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Profile getOrCreateProfile(String id, String email) {
        Profile profile = new Profile();

        var profileDocument = new ProfileDocument();
        profileDocument.email = email;

        Query query = query(where("_id").is(id));
        UpdateResult updateResult = mongoTemplate.upsert(query, Update.update("lastLogin", new Date().getTime()).setOnInsert("email", email), ProfileDocument.class);

        if (updateResult.getMatchedCount() == 0) {
            profile.setNewRegistration(true);
        }

        profileDocument = mongoTemplate.findOne(query, ProfileDocument.class);
        if (profileDocument == null) {
            throw new RuntimeException("Missing profile.");
        }

        // Map profile fields here.
        profile.setProfileId(profileDocument.id);
        profile.setNickname(profileDocument.nickname);
        profile.setEmail(profileDocument.email);

        return profile;
    }

    @Override
    public ProfileDocument getProfile(String profileId) {
        return mongoTemplate.findOne(query(where("_id").is(profileId)), ProfileDocument.class);
    }

    @Override
    public void updateProfile(ProfileDocument profileDocument) {
        mongoTemplate.save(profileDocument);
    }

}
