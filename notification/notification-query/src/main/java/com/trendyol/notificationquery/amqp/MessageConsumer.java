package com.trendyol.notificationquery.amqp;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final MessageProducer messageProducer;

    @Autowired
    public MessageConsumer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @RabbitListener(queues = "${rabbit.queues.user-notification-query-queue}")
    public void handleUserQueryQueueMessages(UserMailRequestMessage message) {
        System.out.println(message);

        // With the user email, send a notification to the user
    }
}
