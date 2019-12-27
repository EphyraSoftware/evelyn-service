package org.evelyn.services.task.data.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TaskModel {
    @Id
    public String id;

    public String profileId;
    public String title;
    public String description;
}
