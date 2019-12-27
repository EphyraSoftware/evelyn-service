package org.evelyn.services.calendar.rest.api;

import org.evelyn.services.calendar.api.CalendarService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping(value = "/calendars/import")
    @ResponseBody
    public void importCalendar(final Principal principal, @RequestParam("calendarFile") MultipartFile calendarFile) throws IOException {
        calendarService.importFile(principal.getName(), calendarFile.getInputStream());
    }
}
