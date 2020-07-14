package org.evelyn.services.calendar.impl;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.lang3.StringUtils;
import org.evelyn.services.calendar.api.CalendarService;
import org.evelyn.services.calendar.api.CreateEventModel;
import org.evelyn.services.calendar.data.api.CalendarData;
import org.evelyn.services.calendar.impl.client.ProfileModel;
import org.evelyn.services.calendar.impl.client.ProfileServiceClient;
import org.evelyn.services.calendar.impl.mapper.ICal4jToEvelynMapper;
import org.evelyn.services.calendar.impl.model.CalendarEvent;
import org.evelyn.services.calendar.impl.model.EvelynCalendar;
import org.evelyn.services.calendar.impl.model.ExchangeMeta;
import org.evelyn.services.calendar.impl.model.ICalendarItem;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EvelynCalendarService implements CalendarService {
  private final CalendarData calendarData;
  private final ProfileServiceClient profileServiceClient;

  public EvelynCalendarService(CalendarData calendarData, ProfileServiceClient profileServiceClient) {
    this.calendarData = calendarData;
    this.profileServiceClient = profileServiceClient;
  }

  @Override
  public void importFile(String name, InputStream calendarFile) {
    ProfileModel profile = getProfileModel();

    try {
      Calendar calendar = new CalendarBuilder().build(calendarFile);

      var evelynCalendar = ICal4jToEvelynMapper.map(calendar);

      validateExchangeMeta(evelynCalendar.getExchangeMeta());

      calendarData.saveCalendar(profile, evelynCalendar);
    } catch (IOException | ParserException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<ICalendarItem> getEvents(String name) {
    return calendarData.getEvents(getProfileModel(), CalendarEvent.TYPE);
  }

  @Override
  public ICalendarItem createEvent(CreateEventModel createEventModel) {
    ProfileModel profile = getProfileModel();

    var calendarEvent = new CalendarEvent();

    calendarEvent.setSummary(createEventModel.getSummary());
    calendarEvent.setDescription(createEventModel.getDescription());
    calendarEvent.setStartDate(
            Date.from(createEventModel.getStartDate().atZone(ZoneId.systemDefault())
                    .toInstant())
    );
    calendarEvent.setEndDate(
            Date.from(createEventModel.getEndDate().atZone(ZoneId.systemDefault())
                    .toInstant())
    );

    calendarEvent.setId(UUID.randomUUID().toString() + "@evelyn.org");

    Date created = Date.from(Instant.now());
    calendarEvent.setCreated(created);
    calendarEvent.setLastModified(created);

    var evelynCalendar = new EvelynCalendar();
    evelynCalendar.getCalendarItems().add(calendarEvent);
    calendarData.saveCalendar(profile, evelynCalendar);

    return calendarEvent;
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

  private ProfileModel getProfileModel() {
    var currentProfileResponse = profileServiceClient.getCurrentProfile();
    if (currentProfileResponse.getBody() == null) {
      throw new RuntimeException("Unable to get profile.");
    }

    return currentProfileResponse.getBody();
  }
}
