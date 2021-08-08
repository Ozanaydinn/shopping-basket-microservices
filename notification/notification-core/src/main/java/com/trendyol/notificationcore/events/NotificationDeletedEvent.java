package com.trendyol.notificationcore.events;

import lombok.Builder;
import lombok.Data;

@Data
public class NotificationDeletedEvent {
    private String id;
}
