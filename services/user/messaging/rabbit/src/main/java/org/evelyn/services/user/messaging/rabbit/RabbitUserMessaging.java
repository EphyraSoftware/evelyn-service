package org.evelyn.services.user.messaging.rabbit;

import org.evelyn.services.user.messaging.api.UserMessaging;
import org.evelyn.services.user.messaging.api.model.UserCreatedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserMessaging implements UserMessaging {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendUserCreated(UserCreatedMessage userCreatedMessage) {
        rabbitTemplate.convertAndSend(UserMessaging.USER_CREATED_QUEUE_NAME, userCreatedMessage);
    }
}
