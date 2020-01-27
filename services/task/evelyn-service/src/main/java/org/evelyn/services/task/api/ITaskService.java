package org.evelyn.services.task.api;

import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;

import javax.json.JsonPatch;
import java.util.List;

public interface ITaskService {
  Task createTask(String profileId, CreateTaskRequest createTaskRequest);

  List<Task> getTasks(String profileId);

  Task patchTask(String profileId, String taskId, JsonPatch patchOperation);
}
