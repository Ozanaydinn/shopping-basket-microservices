package com.trendyol.notificationcore.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {
    @Bean
    Queue userNotificationCommandQueue() {
        return new Queue("userNotificationCommandQueue", true);
    }

    @Bean
    Queue userNotificationQueryQueue() {
        return new Queue("userNotificationQueryQueue", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("user-notification");
    }

    @Bean
    Binding basketNotificationCommandBinding(Queue userNotificationCommandQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userNotificationCommandQueue).to(exchange).with("userNotificationCommandKey");
    }

    @Bean
    Binding userNotificationQueryBinding(Queue userNotificationQueryQueue, DirectExchange exchange ) {
        return BindingBuilder.bind(userNotificationQueryQueue).to(exchange).with("userNotificationQueryKey");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
