package org.evelyn.services.calendar.impl.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CalendarEvent implements ICalendarItem {
  public static final String TYPE = "EVENT";

  private String description;
  private String id;
  private Date startDate;
  private EventClass eventClass;
  private Date created;
  private String location;
  private Date lastModified;
  private Integer sequenceNumber;
  private EventStatus status;
  private Transparency transparency;
  private String RRule;
  private String summary;
  private Date endDate;
  private List<String> categories = new ArrayList<>();

  private Boolean startDateHasTimeComponent;
  private Boolean lastModifiedHasTimeComponent;
  private Boolean endDateHasTimeComponent;

  private Map<String, String> unknownProperties = new HashMap<>();

  public void addUnknownProperty(String name, String value) {
    this.unknownProperties.put(name, value);
  }

  @Override
  public String getType() {
    return TYPE;
  }
}
