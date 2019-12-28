package org.evelyn.services.calendar.impl;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.StringUtils;
import org.evelyn.services.calendar.api.CalendarService;
import org.evelyn.services.calendar.impl.mapper.ICal4jToEvelynMapper;
import org.evelyn.services.calendar.impl.model.ExchangeMeta;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class EvelynCalendarService implements CalendarService {
  @Override
  public void importFile(String name, InputStream calendarFile) {
    try {
      Calendar calendar = new CalendarBuilder().build(calendarFile);

      var evelynCalendar = ICal4jToEvelynMapper.map(calendar);

      validateExchangeMeta(evelynCalendar.getExchangeMeta());

      System.out.println("Import ready");
    } catch (IOException | ParserException e) {
      e.printStackTrace();
    }
  }

  private void validateExchangeMeta(ExchangeMeta exchangeMeta) {
    if (!"GREGORIAN".equals(exchangeMeta.getCalendarScale())) {
      throw new RuntimeException("Unsupported calendar scale");
    }

    if (StringUtils.isEmpty(exchangeMeta.getProductId())) {
      throw new RuntimeException("Importing a calendar requires a product id");
    }

    if (!"PUBLISH".equals(exchangeMeta.getMethod())) {
      throw new RuntimeException("Only the PUBLISH method is supported");
    }

    if (!Version.VERSION_2_0.getValue().equals(exchangeMeta.getVersion())) {
      throw new RuntimeException("Only version 2.0 is supported");
    }
  }
}
