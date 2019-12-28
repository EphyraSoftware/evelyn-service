package org.evelyn.services.calendar.impl.mapper;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.evelyn.services.calendar.impl.model.ICalendarItem;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ICal4jToEvelynMapper {
  private static final Map<String, Function<CalendarComponent, ICalendarItem>> componentMappers = Map.of(
          "VEVENT", new VEventToEvelynMapper()
  );

  public static List<ICalendarItem> map(Calendar calendar) {
    return calendar.getComponents()
            .stream()
            .map(ICal4jToEvelynMapper::mapComponent)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }

  private static ICalendarItem mapComponent(CalendarComponent calendarComponent) {
    if (!componentMappers.containsKey(calendarComponent.getName())) {
      return null;
    }

    return componentMappers.get(calendarComponent.getName()).apply(calendarComponent);
  }
}
