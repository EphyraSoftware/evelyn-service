package org.evelyn.services.task.impl;

import org.evelyn.services.task.api.ITaskService;
import org.evelyn.services.task.api.model.CreateTaskRequest;
import org.evelyn.services.task.api.model.Task;
import org.evelyn.services.task.data.mongo.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvelynTaskService implements ITaskService {
    private final ProfileServiceClient profileServiceClient;

    private final TaskRepository taskRepository;

    public EvelynTaskService(ProfileServiceClient profileServiceClient, TaskRepository taskRepository) {
        this.profileServiceClient = profileServiceClient;
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(String name, CreateTaskRequest createTaskRequest) {
        ResponseEntity<ProfileModel> profile = profileServiceClient.getCurrentProfile();
        if (profile.getBody() == null) {
            throw new RuntimeException("Failed to get profile");
        }

        String profileId = profile.getBody().getProfileId();

        var task = new Task();
        task.setProfileId(profileId);
        task.setTitle(createTaskRequest.getTitle());
        task.setDescription(createTaskRequest.getDescription());

        var taskModel = TaskMapper.toTaskModel(task);

        taskRepository.save(taskModel);

        return TaskMapper.toTask(taskModel);
    }

    @Override
    public List<Task> getTasks(String name) {
        ResponseEntity<ProfileModel> profile = profileServiceClient.getCurrentProfile();
        if (profile.getBody() == null) {
            throw new RuntimeException("Failed to get profile");
        }

        String profileId = profile.getBody().getProfileId();

        return taskRepository.findByProfileId(profileId)
                .stream()
                .map(TaskMapper::toTask)
                .collect(Collectors.toList());
    }
}
