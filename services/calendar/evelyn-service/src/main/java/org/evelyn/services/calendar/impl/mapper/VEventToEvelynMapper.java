package org.evelyn.services.calendar.impl.mapper;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Categories;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.EventClass;
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

            Pair<Date, Boolean> dateBooleanPair = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setStartDate(dateBooleanPair.getLeft());
            propertyCalendarEventPair.getRight().setStartDateHasTimeComponent(dateBooleanPair.getRight());
          }),
          Map.entry("CLASS", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setEventClass(EventClass.fromString(value));
          }),
          Map.entry("CREATED", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            Pair<Date, Boolean> dateBooleanPair = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setCreated(dateBooleanPair.getLeft());
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

            Pair<Date, Boolean> dateBooleanPair = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setLastModified(dateBooleanPair.getLeft());
            propertyCalendarEventPair.getRight().setLastModifiedHasTimeComponent(dateBooleanPair.getRight());
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
          Map.entry("RRULE", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            propertyCalendarEventPair.getRight().setRRule(value);
          }),
          Map.entry("DTEND", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            Pair<Date, Boolean> dateBooleanPair = extractDate(propertyCalendarEventPair.getLeft(), value);

            propertyCalendarEventPair.getRight().setEndDate(dateBooleanPair.getLeft());
            propertyCalendarEventPair.getRight().setEndDateHasTimeComponent(dateBooleanPair.getRight());
          }),
          Map.entry("CATEGORIES", (Pair<Property, CalendarEvent> propertyCalendarEventPair) -> {
            String value = propertyCalendarEventPair.getLeft().getValue();
            if (StringUtils.isEmpty(value)) {
              return;
            }

            new Categories(value).getCategories().iterator().forEachRemaining(
                    s -> propertyCalendarEventPair.getRight().getCategories().add(s)
            );
          })
  );

  private static Pair<Date, Boolean> extractDate(Property property, String value) {
    Parameter valueType = property.getParameter("VALUE");
    Date date = null;
    boolean hasTimeComponent = false;
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
        hasTimeComponent = true;
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    return Pair.of(date, hasTimeComponent);
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
