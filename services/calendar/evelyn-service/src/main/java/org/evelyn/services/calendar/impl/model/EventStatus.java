package org.evelyn.services.calendar.impl.model;

public enum EventStatus {
  TENTATIVE("TENTATIVE"),
  CONFIRMED("CONFIRMED"),
  CANCELLED("CANCELLED");

  private String name;

  EventStatus(String name) {
    this.name = name;
  }

  public static EventStatus fromString(String name) {
    for (EventStatus eventStatus : EventStatus.values()) {
      if (eventStatus.name.equalsIgnoreCase(name)) {
        return eventStatus;
      }
    }
    return null;
  }
}
