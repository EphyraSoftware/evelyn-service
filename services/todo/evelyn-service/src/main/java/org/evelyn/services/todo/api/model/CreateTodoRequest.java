package org.evelyn.services.todo.api.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateTodoRequest {
  @NotNull
  private String name;
  private LocalDateTime expiry;
  private List<CreateTodoItemRequest> initialItems;
}
