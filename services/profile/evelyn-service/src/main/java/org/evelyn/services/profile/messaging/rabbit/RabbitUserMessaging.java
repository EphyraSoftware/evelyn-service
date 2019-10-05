package org.evelyn.services.profile.messaging.rabbit;

import org.evelyn.services.profile.messaging.api.EmailModel;
import org.evelyn.services.profile.messaging.api.UserMessaging;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserMessaging implements UserMessaging {
  @Value("${org.evelyn.profile.email-input-queue-name}")
  private String queueName;

  private final RabbitTemplate rabbitTemplate;

  public RabbitUserMessaging(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void sendUserRegistered(EmailModel userRegisteredMessage) {
    rabbitTemplate.convertAndSend(queueName, userRegisteredMessage);
  }
}
