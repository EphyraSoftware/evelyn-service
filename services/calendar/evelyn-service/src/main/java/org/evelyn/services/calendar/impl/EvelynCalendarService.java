package org.evelyn.services.calendar.impl;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.evelyn.services.calendar.api.CalendarService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class EvelynCalendarService implements CalendarService {
  @Override
  public void importFile(String name, InputStream calendarFile) {
    try {
      Calendar calendar = new CalendarBuilder().build(calendarFile);
      // calendar.validate();

      calendar.getComponents().forEach(calendarComponent -> {
        calendarComponent.getProperties().forEach(property -> {
          System.out.println(property.getName());
          System.out.println(property.getValue());
        });
      });
    } catch (IOException | ParserException e) {
      e.printStackTrace();
    }
  }
}
