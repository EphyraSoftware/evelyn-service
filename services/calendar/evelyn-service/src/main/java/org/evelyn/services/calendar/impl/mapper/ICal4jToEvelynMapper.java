package org.evelyn.services.calendar.impl.mapper;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;
import org.evelyn.services.calendar.impl.model.ExchangeMeta;
import org.evelyn.services.calendar.impl.model.ICalendarItem;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ICal4jToEvelynMapper {
  private static final Map<String, Function<CalendarComponent, ICalendarItem>> componentMappers = Map.of(
          "VEVENT", new VEventToEvelynMapper()
  );

  public static EvelynCalendar map(Calendar calendar) {
    var evelynCalendar = new EvelynCalendar();

    ExchangeMeta exchangeMeta = new ExchangeMeta();
    evelynCalendar.setExchangeMeta(exchangeMeta);
    exchangeMeta.setProductId(calendar.getProductId().getValue());
    if (calendar.getMethod() != null) {
      exchangeMeta.setMethod(calendar.getMethod().getValue());
    }
    if (calendar.getCalendarScale() != null) {
      exchangeMeta.setCalendarScale(calendar.getCalendarScale().getValue());
    }
    exchangeMeta.setVersion(calendar.getVersion().getValue());

    evelynCalendar.setCalendarItems(calendar.getComponents()
            .stream()
            .map(ICal4jToEvelynMapper::mapComponent)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    return evelynCalendar;
  }

  private static ICalendarItem mapComponent(CalendarComponent calendarComponent) {
    if (!componentMappers.containsKey(calendarComponent.getName())) {
      return null;
    }

    return componentMappers.get(calendarComponent.getName()).apply(calendarComponent);
  }
}
