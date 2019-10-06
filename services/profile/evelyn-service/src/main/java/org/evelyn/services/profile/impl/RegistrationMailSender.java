package org.evelyn.services.profile.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.evelyn.library.messages.EmailMessage;
import org.evelyn.services.profile.api.Profile;
import org.evelyn.services.profile.messaging.api.UserMessaging;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

@Component
public class RegistrationMailSender {
  private final UserMessaging userMessaging;

  private final Configuration freeMarkerConfiguration;

  public RegistrationMailSender(UserMessaging userMessaging, Configuration freeMarkerConfiguration) {
    this.userMessaging = userMessaging;
    this.freeMarkerConfiguration = freeMarkerConfiguration;
  }

  void sendRegistrationMessage(Profile profile) throws IOException, TemplateException {
    Template template = freeMarkerConfiguration.getTemplate("registration.ftl");
    String processedTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, new HashMap<>());

    EmailMessage emailModel = new EmailMessage();
    emailModel.setSubject("Welcome from Evelyn");
    emailModel.setBody(processedTemplate);
    emailModel.setRecipients(Collections.singletonList(profile.getEmail()));

    userMessaging.sendUserRegistered(emailModel);
  }
}
