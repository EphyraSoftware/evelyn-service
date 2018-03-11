package org.evelyn.services.group.data.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Group {
    @Id
    public String id;

    public String groupId;
    public String name;
    public List<String> userIds;
}
