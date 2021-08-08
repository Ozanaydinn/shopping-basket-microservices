package com.trendyol.notificationcommand.amqp;

import com.trendyol.notificationcommand.commands.CreateNotificationCommand;
import com.trendyol.notificationcore.models.Notification;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageConsumer {
    private final CommandGateway commandGateway;

    @Autowired
    public MessageConsumer(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RabbitListener(queues = "${rabbit.queues.basket-notification-command-queue}")
    public void handleBasketCommandQueueMessages(CreateNotificationMessage message) {
        System.out.println("CReceived message for " + message.getNotification().getType());

        // Create a notification object and a command
        var notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .notificationType(message.getNotification().getType())
                .productId(message.getNotification().getProductId())
                .userId(message.getNotification().getUserId())
                .build();

        var command = CreateNotificationCommand.builder()
                .id(notification.getId())
                .notification(notification)
                .build();
        System.out.println("Command created : " + command);

        commandGateway.send(command);

    }

    @RabbitListener(queues = "${rabbit.queues.basket-notification-query-queue}")
    public void handleBasketQueryQueueMessages(CreateNotificationMessage message) {
        System.out.println("Received message for " + message.getNotification().getProductId());
    }

}
