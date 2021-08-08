package com.trendyol.basketquery.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${rabbit.queues.basket-notification-command-queue}")
    private String commandQueue;

    @Value("${rabbit.queues.basket-notification-query-queue}")
    private String queryQueue;

    @Value("${rabbit.exchanges.basket-notification-exchange}")
    private String exchange;

    @Value("${rabbit.keys.basket-notification-command-key}")
    private String commandKey;

    @Value("${rabbit.keys.basket-notification-query-key}")
    private String queryKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCreateNotificationMessage(CreateNotificationMessage message) {
        rabbitTemplate.convertAndSend(exchange, commandKey, message);
    }
}
