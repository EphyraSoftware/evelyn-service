package org.evelyn.services.calendar.data.api.model;

import org.evelyn.services.calendar.impl.model.EventClass;
import org.evelyn.services.calendar.impl.model.EventStatus;
import org.evelyn.services.calendar.impl.model.Transparency;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class CalendarItem {
  @Id
  public String id;

  public String profileId;

  public String description;
  public LocalDateTime startDate;
  public EventClass eventClass;
  public LocalDateTime created;
  public String location;
  public LocalDateTime lastModified;
  public Integer sequenceNumber;
  public EventStatus status;
  public Transparency transparency;
  public String RRule;
  public String summary;
  public LocalDateTime endDate;
  public List<String> categories = new ArrayList<>();
}
