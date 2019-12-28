package org.evelyn.services.calendar.data.api;

import org.evelyn.services.calendar.impl.client.ProfileModel;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;

public interface CalendarData {
  void saveCalendar(ProfileModel profile, EvelynCalendar evelynCalendar);
}
