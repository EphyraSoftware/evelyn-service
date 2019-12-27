package org.evelyn.services.task.impl;

import org.evelyn.services.task.api.model.Task;
import org.evelyn.services.task.data.api.model.TaskModel;

import java.util.UUID;

public class TaskMapper {
    public static TaskModel toTaskModel(Task task) {
        var taskModel = new TaskModel();

        taskModel.id = task.getTaskId() == null ? UUID.randomUUID().toString() : task.getTaskId();
        taskModel.profileId = task.getProfileId();
        taskModel.title = task.getTitle();
        taskModel.description = task.getDescription();

        return taskModel;
    }

    public static Task toTask(TaskModel taskModel) {
        var task = new Task();

        task.setTaskId(taskModel.id);
        task.setProfileId(taskModel.profileId);

        task.setTitle(taskModel.title);
        task.setDescription(taskModel.description);

        return task;
    }
}
