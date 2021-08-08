package com.trendyol.notificationquery.handlers;

import com.trendyol.notificationcore.dto.NotificationDTO;
import com.trendyol.notificationcore.dto.UserLookupDTO;
import com.trendyol.notificationcore.events.NotificationCreatedEvent;
import com.trendyol.notificationcore.events.NotificationDeletedEvent;
import com.trendyol.notificationcore.events.NotificationUpdatedEvent;
import com.trendyol.notificationcore.models.Notification;
import com.trendyol.notificationquery.amqp.MessageProducer;
import com.trendyol.notificationquery.amqp.UserMailRequestMessage;
import com.trendyol.notificationquery.email.MailService;
import com.trendyol.notificationquery.repositories.NotificationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class NotificationEventHandlerImpl implements NotificationEventHandler{

    private final NotificationRepository notificationRepository;
    private final MessageProducer messageProducer;
    private final WebClient webClient;
    private final MailService mailService;
    @Autowired
    public NotificationEventHandlerImpl(NotificationRepository notificationRepository, MessageProducer messageProducer, MailService mailService) {
        this.notificationRepository = notificationRepository;
        this.messageProducer = messageProducer;
        webClient = WebClient.create();
        this.mailService = mailService;
    }

    @EventHandler
    @Override
    public void on(NotificationCreatedEvent event) {
        /*
        // Create a notification DTO and send a message to user to get the user email
        var notificationDTO = NotificationDTO.builder()
                .id(event.getNotification().getId())
                .productId(event.getNotification().getProductId())
                .type(event.getNotification().getNotificationType())
                .userId(event.getNotification().getUserId())
                .build();

        // create the message
        var message = UserMailRequestMessage.builder()
                .id(UUID.randomUUID().toString())
                .notification(notificationDTO)
                .build();
        System.out.println("Sending message to user: " + message);
        messageProducer.sendUserMailRequestToUser(message);
         */
        UserLookupDTO response = webClient.get()
                .uri("localhost:8082/api/userLookup/byId/" + event.getNotification().getUserId())
                .retrieve()
                .bodyToMono(UserLookupDTO.class).block();
        var user = response.getUsers().get(0);
        // create a new notification
        var notification = Notification.builder()
                .id(event.getNotification().getId())
                .notificationType(event.getNotification().getNotificationType())
                .userId(event.getNotification().getUserId())
                .productId(event.getNotification().getProductId())
                .build();

        var new_notification = notificationRepository.save(notification);

        // send mail
        mailService.sendMail(new_notification.getNotificationType(), user.getEmail());
    }

    @Override
    public void on(NotificationUpdatedEvent event) {

    }

    @Override
    public void on(NotificationDeletedEvent event) {

    }
}
