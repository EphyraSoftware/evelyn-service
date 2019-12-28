package org.evelyn.services.calendar.data.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoCalendarConfig {
  @Value("${org.evelyn.calendar.mongo-connection-string}")
  private String connectionString;

  protected String getDatabaseName() {
    return "evelyn";
  }

  @Bean("mongoCalendarClient")
  public MongoClient mongoClient() {
    return MongoClients.create(connectionString);
  }

  @Bean("mongoCalendarTemplate")
  public MongoTemplate createMongoTemplate() {
    return new MongoTemplate(mongoClient(), getDatabaseName());
  }
}
