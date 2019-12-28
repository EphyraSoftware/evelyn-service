package org.evelyn.services.calendar.impl.mapper;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.TextList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;

import java.text.ParseException;

public class EvelynToICal4jMapper {
  public static Calendar map(EvelynCalendar evelynCalendar) {
    Calendar calendar = new Calendar();

    calendar.getProperties().add(new ProdId(evelynCalendar.getExchangeMeta().getProductId()));
    if (evelynCalendar.getExchangeMeta().getMethod() != null) {
      calendar.getProperties().add(new Method(evelynCalendar.getExchangeMeta().getMethod()));
    }
    if (evelynCalendar.getExchangeMeta().getCalendarScale() != null) {
      calendar.getProperties().add(new CalScale(evelynCalendar.getExchangeMeta().getCalendarScale()));
    }
    calendar.getProperties().add(new Version(new ParameterList(), evelynCalendar.getExchangeMeta().getVersion()));

    evelynCalendar.getCalendarItems().forEach(calendarItem -> {
      if (calendarItem instanceof CalendarEvent) {
        CalendarEvent calendarEvent = (CalendarEvent) calendarItem;

        CalendarComponent calendarComponent = new VEvent();
        calendarComponent.getProperties().add(new DtStamp());
        calendarComponent.getProperties().add(new Uid(calendarEvent.getId()));
        if (calendarEvent.getStartDateHasTimeComponent()) {
          calendarComponent.getProperties().add(new DtStart(new DateTime(calendarEvent.getStartDate())));
        } else {
          calendarComponent.getProperties().add(new DtStart(new Date(calendarEvent.getStartDate())));
        }
        if (calendarEvent.getEventClass() != null) {
          calendarComponent.getProperties().add(new Clazz(calendarEvent.getEventClass().toString()));
        }
        if (calendarEvent.getCreated() != null) {
          calendarComponent.getProperties().add(new Created(new DateTime(calendarEvent.getCreated())));
        }
        calendarComponent.getProperties().add(new Description(calendarEvent.getDescription()));
        if (calendarEvent.getLastModified() != null) {
          calendarComponent.getProperties().add(new LastModified(new DateTime(calendarEvent.getLastModified())));
        }
        calendarComponent.getProperties().add(new Location(calendarEvent.getLocation()));
        if (calendarEvent.getSequenceNumber() != null) {
          calendarComponent.getProperties().add(new Sequence(calendarEvent.getSequenceNumber()));
        }
        if (calendarEvent.getStatus() != null) {
          calendarComponent.getProperties().add(new Status(calendarEvent.getStatus().toString()));
        }
        calendarComponent.getProperties().add(new Summary(calendarEvent.getSummary()));
        if (calendarEvent.getTransparency() != null) {
          calendarComponent.getProperties().add(new Transp(calendarEvent.getTransparency().toString()));
        }
        if (calendarEvent.getRRule() != null) {
          try {
            calendarComponent.getProperties().add(new RRule(calendarEvent.getRRule()));
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        if (calendarEvent.getEndDate() != null) {
          if (calendarEvent.getEndDateHasTimeComponent()) {
            calendarComponent.getProperties().add(new DtEnd(new DateTime(calendarEvent.getEndDate())));
          } else {
            calendarComponent.getProperties().add(new DtEnd(new Date(calendarEvent.getEndDate())));
          }
        }
        if (!calendarEvent.getCategories().isEmpty()) {
          calendarComponent.getProperties().add(new Categories(new TextList(calendarEvent.getCategories().toArray(new String[0]))));
        }

        calendar.getComponents().add(calendarComponent);
      }
    });

    return calendar;
  }
}
