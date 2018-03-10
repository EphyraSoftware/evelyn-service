package org.evelyn.services.group.data.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private String name;
    private List<String> userList;
}
