package com.springbank.user.core.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Bean
    Queue userBasketCommandQueue() {
        return new Queue("userBasketCommandQueue", false);
    }

    @Bean
    Queue userBasketQueryQueue() {
        return new Queue("userBasketQueryQueue", false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("user-basket");
    }

    @Bean
    Binding userBasketCommandBinding(Queue userBasketCommandQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userBasketCommandQueue).to(exchange).with("userBasketCommandKey");
    }

    @Bean
    Binding userBasketQueryBinding(Queue userBasketQueryQueue, DirectExchange exchange ) {
        return BindingBuilder.bind(userBasketQueryQueue).to(exchange).with("userBasketQueryKey");
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
