package com.trendyol.notificationcore.events;

import com.trendyol.notificationcore.models.Notification;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationUpdatedEvent {
    private String id;
    private Notification notification;
}
