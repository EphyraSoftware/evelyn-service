package org.evelyn.services.todo.api.model;

import lombok.Data;

@Data
public class CreateTodoItemRequest {
  private String text;
}
