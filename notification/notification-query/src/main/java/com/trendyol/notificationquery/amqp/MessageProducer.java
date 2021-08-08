package com.trendyol.notificationquery.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${rabbit.queues.user-notification-command-queue}")
    private String commandQueue;

    @Value("${rabbit.queues.user-notification-query-queue}")
    private String queryQueue;

    @Value("${rabbit.exchanges.user-notification-exchange}")
    private String exchange;

    @Value("${rabbit.keys.user-notification-command-key}")
    private String commandKey;

    @Value("${rabbit.keys.user-notification-query-key}")
    private String queryKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserMailRequestToUser(UserMailRequestMessage message) {
        rabbitTemplate.convertAndSend(exchange, commandKey, message);
    }
}