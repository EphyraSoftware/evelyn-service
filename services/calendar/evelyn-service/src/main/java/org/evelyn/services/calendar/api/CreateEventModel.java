package org.evelyn.services.calendar.api;

import lombok.Data;

import java.time.Instant;

@Data
public class CreateEventModel {
  private String summary;
  private String description;
  private Instant startDate;
  private Instant endDate;
}
