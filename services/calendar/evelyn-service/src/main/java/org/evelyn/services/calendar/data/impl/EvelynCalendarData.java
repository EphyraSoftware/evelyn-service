package org.evelyn.services.calendar.data.impl;

import org.evelyn.services.calendar.data.api.CalendarData;
import org.evelyn.services.calendar.data.api.model.CalendarItem;
import org.evelyn.services.calendar.impl.client.ProfileModel;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;
import org.evelyn.services.calendar.impl.model.ICalendarItem;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class EvelynCalendarData implements CalendarData {
  private final MongoTemplate mongoTemplate;

  public EvelynCalendarData(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void saveCalendar(ProfileModel profile, EvelynCalendar evelynCalendar) {
    List<CalendarItem> calendarItems = toPersistenceModel(profile, evelynCalendar);

    calendarItems.forEach(mongoTemplate::insert);
  }

  @Override
  public List<ICalendarItem> getEvents(ProfileModel profileModel, String type) {
    List<CalendarItem> calendarItems = mongoTemplate.find(query(where("profileId").is(profileModel.getProfileId()).and("itemType").is(type)), CalendarItem.class);
    return toAppModel(calendarItems);
  }

  private List<ICalendarItem> toAppModel(List<CalendarItem> calendarItems) {
    return calendarItems.stream().map(calendarItem -> {
      if (CalendarEvent.TYPE.equals(calendarItem.itemType)) {
        var appCalendarItem = new CalendarEvent();

        appCalendarItem.setId(calendarItem.id);
        appCalendarItem.setDescription(calendarItem.description);
        appCalendarItem.setStartDate(
                Date.from(calendarItem.startDate.atZone(ZoneId.systemDefault())
                        .toInstant())
        );
        appCalendarItem.setEventClass(calendarItem.eventClass);
        appCalendarItem.setCreated(
                Date.from(calendarItem.created.atZone(ZoneId.systemDefault())
                        .toInstant())
        );
        appCalendarItem.setLocation(calendarItem.location);
        appCalendarItem.setLastModified(
                Date.from(calendarItem.lastModified.atZone(ZoneId.systemDefault())
                        .toInstant())
        );
        appCalendarItem.setSequenceNumber(calendarItem.sequenceNumber);
        appCalendarItem.setStatus(calendarItem.status);
        appCalendarItem.setTransparency(calendarItem.transparency);
        appCalendarItem.setRRule(calendarItem.RRule);
        appCalendarItem.setSummary(calendarItem.summary);
        appCalendarItem.setEndDate(
                Date.from(calendarItem.endDate.atZone(ZoneId.systemDefault())
                        .toInstant())
        );
        appCalendarItem.getCategories().addAll(calendarItem.categories);

        return appCalendarItem;
      }

      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  private List<CalendarItem> toPersistenceModel(ProfileModel profile, EvelynCalendar evelynCalendar) {
    return evelynCalendar.getCalendarItems().stream().map(iCalendarItem -> {
      if (iCalendarItem instanceof CalendarEvent) {
        CalendarItem calendarItem = calendarEventToPersistenceModel((CalendarEvent) iCalendarItem);

        calendarItem.profileId = profile.getProfileId();
        calendarItem.itemType = iCalendarItem.getType();

        return calendarItem;
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
