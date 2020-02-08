package org.evelyn.services.todo.api.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Todo {
  private String id;
  private String name;
  private LocalDateTime expiry;
  private List<TodoItem> items = new ArrayList<>();
  private String profileId;
}
