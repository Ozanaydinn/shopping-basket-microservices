package com.trendyol.basketcore.config;

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
    Queue basketNotificationCommandQueue() {
        return new Queue("basketNotificationCommandQueue", true);
    }

    @Bean
    Queue basketNotificationQueryQueue() {
        return new Queue("basketNotificationQueryQueue", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("basket-notification");
    }

    @Bean
    Binding basketNotificationCommandBinding(Queue basketNotificationCommandQueue, DirectExchange exchange) {
        return BindingBuilder.bind(basketNotificationCommandQueue).to(exchange).with("basketNotificationCommandKey");
    }

    @Bean
    Binding basketNotificationQueryBinding(Queue basketNotificationQueryQueue, DirectExchange exchange ) {
        return BindingBuilder.bind(basketNotificationQueryQueue).to(exchange).with("basketNotificationQueryKey");
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
