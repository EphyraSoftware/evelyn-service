package org.evelyn.services.task.impl;

import org.evelyn.services.task.api.ITaskService;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.evelyn.services.task.data.mongo.TaskRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

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
}
