package org.evelyn.services.todo.api;

import org.evelyn.services.todo.api.model.CreateTodoRequest;
import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.api.model.TodoItem;

import java.util.List;

public interface ITodoService {
  Todo createTodo(String profileId, CreateTodoRequest createTodoRequest);

  List<Todo> getTodos(String profileId);

  void putTodoItem(String profileId, String todoId, Integer itemIndex, TodoItem todoItem);
}
