package org.evelyn.services.todo.impl;

import org.evelyn.services.todo.api.model.Todo;
import org.evelyn.services.todo.api.model.TodoItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoMapperTest {
  @Test
  public void roundTipTest() {
    var start = new Todo();
    start.setId("id");
    start.setProfileId("profile-id");
    start.setName("name");
    start.setExpiry(LocalDateTime.now());

    TodoItem startItem = new TodoItem();
    startItem.setText("text");
    startItem.setComplete(true);
    start.getItems().add(startItem);

    var result = TodoMapper.fromModel(TodoMapper.toModel(start));

    assertEquals(start.getId(), result.getId());
    assertEquals(start.getProfileId(), result.getProfileId());
    assertEquals(start.getName(), result.getName());
    assertEquals(start.getExpiry(), result.getExpiry());

    assertEquals(start.getItems().size(), result.getItems().size());
    for (var i = 0; i < start.getItems().size(); i++) {
      assertEquals(start.getItems().get(0).getText(), result.getItems().get(0).getText());
      assertEquals(start.getItems().get(0).isComplete(), result.getItems().get(0).isComplete());
    }
  }
}
