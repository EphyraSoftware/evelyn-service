package org.evelyn.services.calendar.data.api;

import org.evelyn.services.calendar.impl.client.ProfileModel;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;
import org.evelyn.services.calendar.impl.model.ICalendarItem;

import java.util.List;

public interface CalendarData {
  void saveCalendar(ProfileModel profile, EvelynCalendar evelynCalendar);

  List<ICalendarItem> getEvents(ProfileModel profileModel, String type);
}
