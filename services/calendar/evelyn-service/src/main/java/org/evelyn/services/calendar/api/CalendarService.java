package org.evelyn.services.calendar.api;

import org.evelyn.services.calendar.impl.model.ICalendarItem;

import java.io.InputStream;
import java.util.List;

public interface CalendarService {
  void importFile(String name, InputStream calendarFile);

  List<ICalendarItem> getEvents(String name);
}
