package org.evelyn.services.todo.data.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
public class TodoModel {
  @Id
  public String id;
  public String name;
  public String profileId;
  public LocalDateTime expiry;
  public List<TodoItemModel> todoItems;
}
