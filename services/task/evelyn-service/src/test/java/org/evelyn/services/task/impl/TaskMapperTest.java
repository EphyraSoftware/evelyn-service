package org.evelyn.services.task.impl;

import org.evelyn.services.task.api.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskMapperTest {
  @Test
  public void roundTripTest() {
    var sourceTask = new Task();
    sourceTask.setTaskId("task-id");
    sourceTask.setProfileId("profile-id");
    sourceTask.setTitle("title");
    sourceTask.setDescription("description");
    sourceTask.setCompleted(true);
    sourceTask.setCreatedDateTime(LocalDateTime.now());

    var resultTask = TaskMapper.toTask(TaskMapper.toTaskModel(sourceTask));

    assertEquals(sourceTask.getTaskId(), resultTask.getTaskId());
    assertEquals(sourceTask.getProfileId(), resultTask.getProfileId());
    assertEquals(sourceTask.getTitle(), resultTask.getTitle());
    assertEquals(sourceTask.getDescription(), resultTask.getDescription());
    assertEquals(sourceTask.isCompleted(), resultTask.isCompleted());
    assertEquals(sourceTask.getCreatedDateTime(), resultTask.getCreatedDateTime());
  }
}
