package org.evelyn.services.user.data.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoUserConfig extends AbstractMongoConfiguration {
    @Override
    public MongoClient mongoClient() {
        return new MongoClient("0.0.0.0", 9081);
    }

    @Override
    protected String getDatabaseName() {
        return "evelyn";
    }
}
