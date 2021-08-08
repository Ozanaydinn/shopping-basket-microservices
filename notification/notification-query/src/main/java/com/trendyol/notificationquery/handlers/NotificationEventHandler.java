package com.trendyol.notificationquery.handlers;

import com.trendyol.notificationcore.events.NotificationCreatedEvent;
import com.trendyol.notificationcore.events.NotificationDeletedEvent;
import com.trendyol.notificationcore.events.NotificationUpdatedEvent;

public interface NotificationEventHandler {
    void on(NotificationCreatedEvent event);
    void on(NotificationUpdatedEvent event);
    void on(NotificationDeletedEvent event);
}
