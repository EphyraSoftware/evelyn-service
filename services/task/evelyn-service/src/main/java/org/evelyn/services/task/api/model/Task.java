package org.evelyn.services.task.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private String taskId;
    private String profileId;

    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdDateTime;
}
