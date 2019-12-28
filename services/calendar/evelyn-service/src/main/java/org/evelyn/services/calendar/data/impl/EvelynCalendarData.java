package org.evelyn.services.calendar.data.impl;

import org.evelyn.services.calendar.data.api.CalendarData;
import org.evelyn.services.calendar.data.api.model.CalendarItem;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvelynCalendarData implements CalendarData {
  private final MongoTemplate mongoTemplate;

  public EvelynCalendarData(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void saveCalendar(EvelynCalendar evelynCalendar) {
    List<CalendarItem> calendarItems = toPersistenceModel(evelynCalendar);
    calendarItems.forEach(mongoTemplate::insert);
  }

  private List<CalendarItem> toPersistenceModel(EvelynCalendar evelynCalendar) {
    return evelynCalendar.getCalendarItems().stream().map(iCalendarItem -> {
      if (iCalendarItem instanceof CalendarEvent) {
        return calendarEventToPersistenceModel((CalendarEvent) iCalendarItem);
      }

      return null;
    }).collect(Collectors.toList());
  }

  private CalendarItem calendarEventToPersistenceModel(CalendarEvent calendarEvent) {
    var calendarItem = new CalendarItem();

    calendarItem.id = calendarEvent.getId();
    calendarItem.description = calendarEvent.getDescription();
    calendarItem.startDate = calendarEvent.getStartDate()
            .toInstant()
            .atZone(ZoneId.systemDefault()) // Test this! Doesn't look like a good idea.
            .toLocalDateTime();
    calendarItem.eventClass = calendarEvent.getEventClass();
    calendarItem.created = calendarEvent.getCreated()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    calendarItem.location = calendarEvent.getLocation();
    calendarItem.lastModified = calendarEvent.getLastModified()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    calendarItem.sequenceNumber = calendarEvent.getSequenceNumber();
    calendarItem.status = calendarEvent.getStatus();
    calendarItem.transparency = calendarEvent.getTransparency();
    calendarItem.RRule = calendarEvent.getRRule();
    calendarItem.summary = calendarEvent.getSummary();
    calendarItem.endDate = calendarEvent.getEndDate()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    calendarItem.categories.addAll(calendarEvent.getCategories());

    return calendarItem;
  }
}
