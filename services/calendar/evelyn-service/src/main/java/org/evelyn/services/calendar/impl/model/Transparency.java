package org.evelyn.services.calendar.impl.model;

public enum Transparency {
  OPAQUE("OPAQUE"),
  TRANSPARENT("TRANSPARENT");

  private String name;

  Transparency(String name) {
    this.name = name;
  }

  public static Transparency fromString(String name) {
    for (Transparency eventStatus : Transparency.values()) {
      if (eventStatus.name.equalsIgnoreCase(name)) {
        return eventStatus;
      }
    }
    return null;
  }
}
