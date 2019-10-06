package org.evelyn.library.messages;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EmailMessage implements Serializable {
  private List<String> recipients;
  private String subject;
  private String body;
}
