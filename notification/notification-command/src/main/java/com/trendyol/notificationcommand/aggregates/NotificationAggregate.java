package com.trendyol.notificationcommand.aggregates;

import com.trendyol.notificationcommand.commands.CreateNotificationCommand;
import com.trendyol.notificationcommand.commands.DeleteNotificationCommand;
import com.trendyol.notificationcommand.commands.UpdateNotificationCommand;
import com.trendyol.notificationcore.events.NotificationCreatedEvent;
import com.trendyol.notificationcore.events.NotificationDeletedEvent;
import com.trendyol.notificationcore.events.NotificationUpdatedEvent;
import com.trendyol.notificationcore.models.Notification;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class NotificationAggregate {
    @AggregateIdentifier
    private String id;

    private Notification notification;

    @CommandHandler
    public NotificationAggregate(CreateNotificationCommand command) {
        var notification = command.getNotification();
        notification.setId(command.getId());

        var event = NotificationCreatedEvent.builder()
                .id(command.getId())
                .notification(notification)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(NotificationCreatedEvent event) {
        this.id = event.getId();
        this.notification = event.getNotification();
    }

    @CommandHandler
    public void handle(UpdateNotificationCommand command) {
        var notification = command.getNotification();
        notification.setId(command.getId());

        var event = NotificationUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .notification(notification)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(NotificationUpdatedEvent event) {
        this.notification = event.getNotification();
    }

    @CommandHandler
    public void handle(DeleteNotificationCommand command) {
        var event = new NotificationDeletedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(NotificationDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
