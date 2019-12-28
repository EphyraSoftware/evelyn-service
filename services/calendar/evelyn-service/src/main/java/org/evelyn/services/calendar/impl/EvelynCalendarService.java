package org.evelyn.services.calendar.impl;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.evelyn.services.calendar.api.CalendarService;
import org.evelyn.services.calendar.impl.mapper.ICal4jToEvelynMapper;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.ICalendarItem;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class EvelynCalendarService implements CalendarService {
  @Override
  public void importFile(String name, InputStream calendarFile) {
    try {
      Calendar calendar = new CalendarBuilder().build(calendarFile);
      // calendar.validate();

      var evelynCalendar = ICal4jToEvelynMapper.map(calendar);
      List<ICalendarItem> result = evelynCalendar.getCalendarItems();
      CalendarEvent calendarEvent = (CalendarEvent) result.get(0);

      java.util.Calendar cal = java.util.Calendar.getInstance();
      cal.setTime(calendarEvent.getStartDate());
      System.out.println(cal.get(java.util.Calendar.YEAR));

      System.out.println(calendarEvent.getTransparency());

      calendarEvent.getUnknownProperties().forEach((s, s2) -> {
        System.out.println(s + ", " + s2);
      });
    } catch (IOException | ParserException e) {
      e.printStackTrace();
    }
  }
}
