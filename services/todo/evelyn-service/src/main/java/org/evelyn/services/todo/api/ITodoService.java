package org.evelyn.services.todo.api;

import org.evelyn.services.todo.api.model.CreateTodoRequest;
import org.evelyn.services.todo.api.model.Todo;

import java.util.List;

public interface ITodoService {
  Todo createTodo(String profileId, CreateTodoRequest createTodoRequest);

  List<Todo> getTodos(String profileId);
}
