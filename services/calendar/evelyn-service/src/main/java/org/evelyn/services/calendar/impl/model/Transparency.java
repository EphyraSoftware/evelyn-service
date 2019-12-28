package org.evelyn.services.calendar.impl.model;

public enum Transparency {
  OPAQUE("OPAQUE"),
  TRANSPARENT("TRANSPARENT");

  private String name;

  Transparency(String name) {
    this.name = name;
  }

  public static Transparency fromString(String name) {
    for (Transparency transparency : Transparency.values()) {
      if (transparency.name.equalsIgnoreCase(name)) {
        return transparency;
      }
    }
    return null;
  }
}
