package org.evelyn.services.calendar.api;

import java.io.InputStream;

public interface CalendarService {
  void importFile(String name, InputStream calendarFile);
}
