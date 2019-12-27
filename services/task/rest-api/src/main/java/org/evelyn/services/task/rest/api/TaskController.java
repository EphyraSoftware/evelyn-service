package org.evelyn.services.task.rest.api;

import org.evelyn.services.task.api.ITaskService;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TaskController {
    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/tasks")
    @ResponseBody
    public Task createTask(final Principal principal, @RequestBody @Validated CreateTaskRequest createTaskRequest) {
        return taskService.createTask(principal.getName(), createTaskRequest);
    }

    @GetMapping(value = "/tasks")
    @ResponseBody
    public List<Task> getTasks(final Principal principal) {
        return taskService.getTasks(principal.getName());
    }
}
