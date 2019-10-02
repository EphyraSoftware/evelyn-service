package org.evelyn.services.user.data.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoUserConfig extends AbstractMongoClientConfiguration {
    @Value("${org.evelyn.user.mongo-connection-string")
    private String connectionString;

    @Override
    protected String getDatabaseName() {
        return "evelyn";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(connectionString);
    }
}
