package org.evelyn.services.todo.impl;

import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.api.model.TodoItem;
import org.evelyn.services.todo.data.api.model.TodoItemModel;
import org.evelyn.services.todo.data.api.model.TodoModel;

import java.util.stream.Collectors;

public class TodoMapper {
  public static TodoModel toModel(Todo todo) {
    var todoModel = new TodoModel();
    todoModel.id = todo.getId();
    todoModel.profileId = todo.getProfileId();
    todoModel.name = todo.getName();
    todoModel.expiry = todo.getExpiry();

    todoModel.todoItems = todo.getItems().stream().map(todoItem -> {
      var todoItemModel = new TodoItemModel();
      todoItemModel.text = todoItem.getText();
      todoItemModel.complete = todoItem.isComplete();
      return todoItemModel;
    }).collect(Collectors.toList());

    return todoModel;
  }

  public static Todo fromModel(TodoModel result) {
    var todo = new Todo();
    todo.setId(result.id);
    todo.setProfileId(result.profileId);
    todo.setName(result.name);
    todo.setExpiry(result.expiry);

    todo.setItems(result.todoItems.stream().map(todoItemModel -> {
      var todoItem = new TodoItem();
      todoItem.setText(todoItemModel.text);
      todoItem.setComplete(todoItemModel.complete);
      return todoItem;
    }).collect(Collectors.toList()));

    return todo;
  }
}
