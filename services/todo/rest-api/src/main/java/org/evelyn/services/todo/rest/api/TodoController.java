package org.evelyn.services.todo.rest.api;

import org.evelyn.services.todo.api.ITodoService;
import org.evelyn.services.todo.api.model.CreateTodoRequest;
import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.api.model.TodoItem;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TodoController {
    private final ITodoService todoService;

    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping(value = "/todos")
    public Todo createTodo(final Principal principal, @RequestBody @Validated CreateTodoRequest createTodoRequest) {
        return todoService.createTodo(principal.getName(), createTodoRequest);
    }

    @GetMapping(value = "/todos")
    public List<Todo> getTodos(final Principal principal) {
        return todoService.getTodos(principal.getName());
    }

    @PutMapping(value = "/todos/{todoId}/items/{itemIndex}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void putTodoItem(final Principal principal, @PathVariable String todoId, @PathVariable Integer itemIndex, @RequestBody TodoItem todoItem) {
        todoService.putTodoItem(principal.getName(), todoId, itemIndex, todoItem);
    }
}
