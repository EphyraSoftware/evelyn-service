package org.evelyn.services.calendar.impl.model;

public enum EventClass {
  PUBLIC("PUBLIC"),
  PRIVATE("PRIVATE"),
  CONFIDENTIAL("CONFIDENTIAL");

  private String name;

  EventClass(String name) {
    this.name = name;
  }

  public static EventClass fromString(String name) {
    for (EventClass eventClass : EventClass.values()) {
      if (eventClass.name.equalsIgnoreCase(name)) {
        return eventClass;
      }
    }
    return null;
  }
}
