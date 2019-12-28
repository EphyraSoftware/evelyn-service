package org.evelyn.services.calendar.impl.model;

import lombok.Data;

import java.util.List;

@Data
public class EvelynCalendar {
  private ExchangeMeta exchangeMeta;

  private List<ICalendarItem> calendarItems;
}
