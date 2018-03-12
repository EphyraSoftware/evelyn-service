package org.evelyn.services.user.messaging.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.evelyn.services.user.messaging.api.UserMessaging.USER_CREATED_QUEUE_NAME;

@Configuration
public class RabbitConfig {
    @Bean(name = "userCreatedQueue")
    Queue queue() {
        return new Queue(USER_CREATED_QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("user-messaging-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(USER_CREATED_QUEUE_NAME);
    }
}
