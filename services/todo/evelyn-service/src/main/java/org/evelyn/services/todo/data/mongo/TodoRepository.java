package org.evelyn.services.todo.data.mongo;

import org.evelyn.services.todo.data.api.model.TodoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<TodoModel, Long> {

  List<TodoModel> findByProfileId(String profileId);
}
