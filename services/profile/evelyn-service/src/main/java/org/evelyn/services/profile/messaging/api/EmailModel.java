package org.evelyn.services.profile.messaging.api;

import lombok.Data;

import java.util.List;

@Data
public class EmailModel {
  private List<String> recipients;
  private String subject;
  private String body;
}
