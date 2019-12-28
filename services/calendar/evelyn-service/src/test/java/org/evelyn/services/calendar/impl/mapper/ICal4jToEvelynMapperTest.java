package org.evelyn.services.calendar.impl.mapper;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ICal4jToEvelynMapperTest {

  @ParameterizedTest
  @ValueSource(strings = {
          "vevent-1.ics",
          "vevent-2.ics",
          "vevent-3.ics",
          "vevent-4.ics"
  })
  public void roundTripTest(String testFile) throws IOException, ParserException {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(testFile);
    assertNotNull(inputStream);

    Calendar calendar = new CalendarBuilder().build(inputStream);
    EvelynCalendar evelynCalendar = ICal4jToEvelynMapper.map(calendar);

    Calendar fromModel = EvelynToICal4jMapper.map(evelynCalendar);

    assertCalendarsMatch(calendar, fromModel);
  }

  private void assertCalendarsMatch(Calendar calendar, Calendar fromModel) {
    assertEquals(calendar.getProductId(), fromModel.getProductId());
    assertEquals(calendar.getMethod(), fromModel.getMethod());
    assertEquals(calendar.getCalendarScale(), fromModel.getCalendarScale());
    assertEquals(calendar.getVersion(), fromModel.getVersion());

    calendar.getComponents().forEach(calendarComponent -> {
      CalendarComponent fromModelComponent = fromModel.getComponent(calendarComponent.getName());

      assertNotNull(fromModelComponent);
      assertEquals(calendarComponent.getName(), fromModelComponent.getName());

      calendarComponent.getProperties().forEach(property -> {
        Property fromModelComponentProperty = fromModelComponent.getProperty(property.getName());

        assertNotNull(fromModelComponentProperty, "Expected to find property: " + property.getName());
        assertEquals(property.getName(), fromModelComponentProperty.getName());
        if ("DTSTAMP".equals(property.getName())) {
          // Don't assert this field. It is transient.
          return;
        }

        assertEquals(property.getValue(), fromModelComponentProperty.getValue(), "Asserting property: " + property.getName());

        property.getParameters().forEach(parameter -> {
          Parameter fromModelComponentPropertyParameter = fromModelComponentProperty.getParameter(parameter.getName());

          assertNotNull(fromModelComponentPropertyParameter);
          assertEquals(parameter.getName(), fromModelComponentPropertyParameter.getName());
          assertEquals(parameter.getValue(), fromModelComponentPropertyParameter.getValue());
        });
      });
    });
  }
}
