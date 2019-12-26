package org.evelyn.services.task.impl;

import org.evelyn.services.task.api.ITaskService;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.springframework.stereotype.Service;

@Service
public class EvelynTaskService implements ITaskService {
    @Override
    public Task createTask(String name, CreateTaskRequest createTaskRequest) {
        return null;
    }
}
