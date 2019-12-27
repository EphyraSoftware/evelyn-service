package org.evelyn.services.profile.data.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoUserConfig {
    @Value("${org.evelyn.profile.mongo-connection-string}")
    private String connectionString;

    protected String getDatabaseName() {
        return "evelyn";
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate createMongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
