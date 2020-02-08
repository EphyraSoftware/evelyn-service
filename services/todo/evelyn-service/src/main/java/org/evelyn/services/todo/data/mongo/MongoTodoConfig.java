package org.evelyn.services.todo.data.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoTodoConfig extends AbstractMongoClientConfiguration {
  @Value("${org.evelyn.todo.mongo-connection-string}")
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
