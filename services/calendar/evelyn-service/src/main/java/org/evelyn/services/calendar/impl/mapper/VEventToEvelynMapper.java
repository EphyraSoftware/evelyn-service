package org.evelyn.services.calendar.impl.mapper;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.EventStatus;
import org.evelyn.services.calendar.impl.model.ICalendarItem;
import org.evelyn.services.calendar.impl.model.Transparency;

import java.text.ParseException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

class VEventToEvelynMapper implements Function<CalendarComponent, ICalendarItem> {
  private static final Map<String, Consumer<Pair<Property, CalendarEvent>>> propertyMappers = Map.ofEntries(
          Map.entry("DTSTAMP", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            // The timestamp the ics file was created, don't need this at the moment.
          }),
          Map.entry("UID", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            propertyCalendarEventPair.getRight().setId(value);
          }),
          Map.entry("DTSTART", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            Date date = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setStartDate(date);
          }),
          Map.entry("CREATED", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            Date date = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setCreated(date);
          }),
          Map.entry("DESCRIPTION", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setDescription(value);
          }),
          Map.entry("LAST-MODIFIED", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            Date date = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setLastModified(date);
          }),
          Map.entry("LOCATION", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setLocation(value);
          }),
          Map.entry("SEQUENCE", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            var sequenceNumber = Integer.parseUnsignedInt(value);
            propertyCalendarEventPair.getRight().setSequenceNumber(sequenceNumber);
          }),
          Map.entry("STATUS", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setStatus(EventStatus.fromString(value));
          }),
          Map.entry("SUMMARY", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setSummary(value);
          }),
          Map.entry("TRANSP", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setTransparency(Transparency.fromString(value));
          }),
          Map.entry("DTEND", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            Date date = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setEndDate(date);
          })
  );

  private static Date extractDate(Property property, String value) {
    Parameter valueType = property.getParameter("VALUE");
    Date date = null;
    if (valueType != null && !StringUtils.isEmpty(valueType.getValue()) && "DATE".equals(valueType.getValue())) {
      try {
        date = new Date(value);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } else {
      try {
        // TODO Look at using modern date handling.
        // LocalDate date = LocalDate.parse(value, DateTimeFormatter.BASIC_ISO_DATE);
        date = new DateTime(value);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    return date;
  }

  @Override
  public CalendarEvent apply(CalendarComponent calendarComponent) {
    var calendarEvent = new CalendarEvent();

    calendarComponent.getProperties().forEach(property -> {
      if (propertyMappers.containsKey(property.getName())) {
        propertyMappers.get(property.getName()).accept(Pair.of(property, calendarEvent));
      } else {
        calendarEvent.addUnknownProperty(property.getName(), property.getValue());
      }
    });

    return calendarEvent;
  }
}
