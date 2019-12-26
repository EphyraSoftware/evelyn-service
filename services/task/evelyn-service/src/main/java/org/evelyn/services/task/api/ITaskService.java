package org.evelyn.services.task.api;

import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;

public interface ITaskService {
    Task createTask(String name, CreateTaskRequest createTaskRequest);
}
