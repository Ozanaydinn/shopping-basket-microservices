package com.trendyol.productcore.config;

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
public class RabbitMQConfig {
    @Bean
    Queue productBasketCommandQueue() {
        return new Queue("productBasketCommandQueue", true);
    }

    @Bean
    Queue productBasketQueryQueue() {
        return new Queue("productBasketQueryQueue", true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("product-basket");
    }

    @Bean
    Binding productBasketCommandBinding(Queue productBasketCommandQueue, DirectExchange exchange) {
        return BindingBuilder.bind(productBasketCommandQueue).to(exchange).with("productBasketCommandKey");
    }

    @Bean
    Binding productBasketQueryBinding(Queue productBasketQueryQueue, DirectExchange exchange ) {
        return BindingBuilder.bind(productBasketQueryQueue).to(exchange).with("productBasketQueryKey");
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
