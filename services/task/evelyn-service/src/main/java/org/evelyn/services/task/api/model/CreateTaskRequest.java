package org.evelyn.services.task.api.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateTaskRequest {
    @NotEmpty
    private String title;
    private String description;
}
