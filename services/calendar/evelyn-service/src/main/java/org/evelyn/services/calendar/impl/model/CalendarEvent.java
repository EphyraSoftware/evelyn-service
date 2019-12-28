package org.evelyn.services.calendar.impl.model;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class CalendarEvent implements ICalendarItem {
  private String description;
  private String id;
  private Date startDate;
  private Map<String, String> unknownProperties = new HashMap<>();
  private Date created;
  private String location;
  private Date lastModified;
  private Integer sequenceNumber;
  private EventStatus status;
  private Transparency transparency;
  private String summary;
  private Date endDate;

  public void addUnknownProperty(String name, String value) {
    this.unknownProperties.put(name, value);
  }
}
