package org.evelyn.services.task.api;

import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;

import java.util.List;

public interface ITaskService {
    Task createTask(String name, CreateTaskRequest createTaskRequest);

    List<Task> getTasks(String name);
}
