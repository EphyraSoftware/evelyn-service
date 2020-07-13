package org.evelyn.services.todo.rest.api;

import org.evelyn.library.authentication.token.TokenLookup;
import org.evelyn.services.todo.api.ITodoService;
import org.evelyn.services.todo.api.model.CreateTodoRequest;
import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.api.model.TodoItem;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TodoController {
    private final ITodoService todoService;
    private final TokenLookup tokenLookup;

    public TodoController(ITodoService todoService, TokenLookup tokenLookup) {
        this.todoService = todoService;
        this.tokenLookup = tokenLookup;
    }

    @PostMapping(value = "/todos")
    public Todo createTodo(@AuthenticationPrincipal Jwt principal, @RequestBody @Validated CreateTodoRequest createTodoRequest) {
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        return todoService.createTodo(tokenInfo.getSubject(), createTodoRequest);
    }

    @GetMapping(value = "/todos")
    public List<Todo> getTodos(@AuthenticationPrincipal Jwt principal) {
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        return todoService.getTodos(tokenInfo.getSubject());
    }

    @PutMapping(value = "/todos/{todoId}/items/{itemIndex}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void putTodoItem(@AuthenticationPrincipal Jwt principal, @PathVariable String todoId, @PathVariable Integer itemIndex, @RequestBody TodoItem todoItem) {
        var tokenInfo = tokenLookup.getTokenInfo(principal);
        todoService.putTodoItem(tokenInfo.getSubject(), todoId, itemIndex, todoItem);
    }
}
