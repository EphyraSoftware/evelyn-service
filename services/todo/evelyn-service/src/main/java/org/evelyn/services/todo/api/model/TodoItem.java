package org.evelyn.services.todo.api.model;

import lombok.Data;

@Data
public class TodoItem {
  private String text;
  private boolean complete;
}
