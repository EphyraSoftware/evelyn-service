package org.evelyn.services.calendar.rest.api;

import org.evelyn.library.authentication.token.TokenLookup;
import org.evelyn.services.calendar.api.CalendarService;
import org.evelyn.services.calendar.api.CreateEventModel;
import org.evelyn.services.calendar.impl.model.ICalendarItem;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CalendarController {
  private final CalendarService calendarService;
  private final TokenLookup tokenLookup;

  public CalendarController(CalendarService calendarService, TokenLookup tokenLookup) {
    this.calendarService = calendarService;
    this.tokenLookup = tokenLookup;
  }

  @GetMapping(value = "/calendars/events")
  @ResponseBody
  public List<ICalendarItem> getEvents(@AuthenticationPrincipal Jwt principal) {
    var tokenInfo = tokenLookup.getTokenInfo(principal);
    return calendarService.getEvents(tokenInfo.getSubject());
  }

  @PostMapping(value = "/calendars/events")
  @ResponseBody
  public ICalendarItem createEvent(@AuthenticationPrincipal Jwt principal, @RequestBody CreateEventModel createEventModel) {
    tokenLookup.getTokenInfo(principal);
    return calendarService.createEvent(createEventModel);
  }

  @PostMapping(value = "/calendars/import")
  @ResponseBody
  public void importCalendar(@AuthenticationPrincipal Jwt principal, @RequestParam("calendarFile") MultipartFile calendarFile) throws IOException {
    var tokenInfo = tokenLookup.getTokenInfo(principal);
    calendarService.importFile(tokenInfo.getSubject(), calendarFile.getInputStream());
  }
}
