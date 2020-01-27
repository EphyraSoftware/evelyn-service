package org.evelyn.services.task.impl;

import com.google.gson.Gson;
import org.evelyn.services.task.api.ITaskService;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.evelyn.services.task.data.mongo.TaskRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableMongoRepositories(basePackages = "org.evelyn.services.task.data.mongo")
public class EvelynTaskService implements ITaskService {

    private final TaskRepository taskRepository;

  public EvelynTaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public Task createTask(String profileId, CreateTaskRequest createTaskRequest) {
    var task = new Task();
    task.setProfileId(profileId);
    task.setTitle(createTaskRequest.getTitle());
    task.setDescription(createTaskRequest.getDescription());
    task.setCompleted(false);
    task.setCreatedDateTime(LocalDateTime.now());

    var taskModel = TaskMapper.toTaskModel(task);

    taskRepository.save(taskModel);

    return TaskMapper.toTask(taskModel);
  }

  @Override
  public List<Task> getTasks(String profileId) {
    return taskRepository.findByProfileId(profileId)
            .stream()
            .map(TaskMapper::toTask)
            .collect(Collectors.toList());
  }

  @Override
  public Task patchTask(String profileId, String taskId, JsonPatch patchOperation) {
    var task = getTask(profileId, taskId);

    JsonReader reader = Json.createReader(new StringReader(new Gson().toJson(task)));
    JsonObject t = reader.readObject();
    JsonObject patched = patchOperation.apply(t);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    JsonWriter w = Json.createWriter(out);
    w.writeObject(patched);
    Task patchedTask = new Gson().fromJson(new StringReader(new String(out.toByteArray())), Task.class);

    if (task.getCreatedDateTime().compareTo(patchedTask.getCreatedDateTime()) != 0) {
      throw new IllegalStateException("User may not change created date time.");
    }

    taskRepository.save(TaskMapper.toTaskModel(patchedTask));

    return patchedTask;
  }

  private Task getTask(String profileId, String taskId) {
    var taskModel = taskRepository.findByProfileIdAndId(profileId, taskId);
    return TaskMapper.toTask(taskModel);
  }
}
