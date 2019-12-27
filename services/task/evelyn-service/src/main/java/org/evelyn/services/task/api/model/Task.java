package org.evelyn.services.task.api.model;

import lombok.Data;

@Data
public class Task {
    private String taskId;
    private String profileId;

    private String title;
    private String description;
}
