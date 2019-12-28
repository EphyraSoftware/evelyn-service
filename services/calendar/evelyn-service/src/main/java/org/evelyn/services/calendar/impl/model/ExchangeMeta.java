package org.evelyn.services.calendar.impl.model;

import lombok.Data;

@Data
public class ExchangeMeta {
  private String productId;
  private String method;
  private String calendarScale;
  private String maxVersion;
  private String minVersion;
}
