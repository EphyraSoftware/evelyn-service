package org.evelyn.services.calendar.rest.api;

import org.evelyn.services.calendar.api.CalendarService;
import org.evelyn.services.calendar.api.CreateEventModel;
import org.evelyn.services.calendar.impl.model.ICalendarItem;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CalendarController {
  private final CalendarService calendarService;

  public CalendarController(CalendarService calendarService) {
    this.calendarService = calendarService;
  }

  @GetMapping(value = "/calendars/events")
  @ResponseBody
  public List<ICalendarItem> getEvents(final Principal principal) {
    return calendarService.getEvents(principal.getName());
  }

  @PostMapping(value = "/calendars/events")
  @ResponseBody
  public ICalendarItem createEvent(@RequestBody CreateEventModel createEventModel) {
    return calendarService.createEvent(createEventModel);
  }

  @PostMapping(value = "/calendars/import")
  @ResponseBody
  public void importCalendar(final Principal principal, @RequestParam("calendarFile") MultipartFile calendarFile) throws IOException {
    calendarService.importFile(principal.getName(), calendarFile.getInputStream());
  }
}
