package com.trendyol.notificationcore.dto;

import com.trendyol.notificationcore.models.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Serializable {
    private String id;
    private String userId;
    private NotificationType type;
    private String productId;
}
