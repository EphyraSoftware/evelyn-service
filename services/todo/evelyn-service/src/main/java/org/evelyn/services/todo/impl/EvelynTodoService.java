package org.evelyn.services.todo.impl;

import org.evelyn.services.todo.api.ITodoService;
import org.evelyn.services.todo.api.model.CreateTodoRequest;
import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.api.model.TodoItem;
import org.evelyn.services.todo.data.api.model.TodoModel;
import org.evelyn.services.todo.data.mongo.TodoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.evelyn.services.todo.impl.TodoMapper.fromModel;
import static org.evelyn.services.todo.impl.TodoMapper.toModel;

@Service
@EnableMongoRepositories(basePackages = "org.evelyn.services.todo.data.mongo")
public class EvelynTodoService implements ITodoService {

  private final TodoRepository todoRepository;

  public EvelynTodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  public Todo createTodo(String profileId, CreateTodoRequest createTodoRequest) {
    Todo todo = buildTodoFromCreateRequest(createTodoRequest);
    todo.setProfileId(profileId);

    TodoModel todoModel = toModel(todo);

    var result = todoRepository.save(todoModel);

    return fromModel(result);
  }

  @Override
  public List<Todo> getTodos(String profileId) {
    List<TodoModel> todos = todoRepository.findByProfileId(profileId);

    return todos.stream().map(TodoMapper::fromModel).collect(Collectors.toList());
  }

  @Override
  public void putTodoItem(String profileId, String todoId, Integer itemIndex, TodoItem todoItem) {
    TodoModel todoModel = todoRepository.findByProfileIdAndId(profileId, todoId);

    var todo = fromModel(todoModel);

    TodoItem item = todo.getItems().get(itemIndex);
    item.setText(todoItem.getText());
    item.setComplete(todoItem.isComplete());

    todoRepository.save(toModel(todo));
  }

  private Todo buildTodoFromCreateRequest(CreateTodoRequest createTodoRequest) {
    var todo = new Todo();
    todo.setName(createTodoRequest.getName());
    todo.setExpiry(createTodoRequest.getExpiry());

    if (createTodoRequest.getInitialItems() != null) {
      todo.setItems(createTodoRequest.getInitialItems().stream().map(initialTodoItem -> {
        var todoItem = new TodoItem();
        todoItem.setText(initialTodoItem.getText());
        return todoItem;
      }).collect(Collectors.toList()));
    }

    return todo;
  }
}
