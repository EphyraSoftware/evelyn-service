package org.evelyn.services.task.rest.api;

import org.evelyn.library.authentication.token.TokenLookup;
import org.evelyn.services.task.api.ITaskService;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import java.io.StringReader;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TaskController {
    private final ITaskService taskService;
    private final TokenLookup tokenLookup;

    public TaskController(ITaskService taskService, TokenLookup tokenLookup) {
        this.taskService = taskService;
        this.tokenLookup = tokenLookup;
    }

    @PostMapping(value = "/tasks")
    @ResponseBody
    public Task createTask(@AuthenticationPrincipal Jwt principal, @RequestBody @Validated CreateTaskRequest createTaskRequest) {
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        return taskService.createTask(tokenInfo.getSubject(), createTaskRequest);
    }

    @GetMapping(value = "/tasks")
    @ResponseBody
    public List<Task> getTasks(@AuthenticationPrincipal Jwt principal) {
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        return taskService.getTasks(tokenInfo.getSubject());
    }

    @PatchMapping(value = "/tasks/{taskId}")
    @ResponseBody
    public Task patchTask(@AuthenticationPrincipal Jwt principal, @PathVariable String taskId, @RequestBody String patchOperations) {
        var jsonArray = Json.createReader(new StringReader(patchOperations)).readArray();
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        return taskService.patchTask(tokenInfo.getSubject(), taskId, Json.createPatch(jsonArray));
    }

    @DeleteMapping(value = "/tasks/{taskId}")
    @ResponseBody
    public void deleteTask(@AuthenticationPrincipal Jwt principal, @PathVariable String taskId) {
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        taskService.deleteTask(tokenInfo.getSubject(), taskId);
    }
}
