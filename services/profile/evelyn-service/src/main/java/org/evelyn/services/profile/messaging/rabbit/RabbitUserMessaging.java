package org.evelyn.services.profile.messaging.rabbit;

import org.evelyn.services.profile.messaging.api.UserMessaging;
import org.evelyn.services.profile.messaging.api.model.UserCreatedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserMessaging implements UserMessaging {
    private final RabbitTemplate rabbitTemplate;

	public RabbitUserMessaging(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
    public void sendUserCreated(UserCreatedMessage userCreatedMessage) {
        rabbitTemplate.convertAndSend(UserMessaging.USER_CREATED_QUEUE_NAME, userCreatedMessage);
    }
}
