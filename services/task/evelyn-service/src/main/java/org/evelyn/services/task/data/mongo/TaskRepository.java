package org.evelyn.services.task.data.mongo;

import org.evelyn.services.task.data.api.model.TaskModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<TaskModel, Long> {
    List<TaskModel> findByProfileId(String profileId);
}
