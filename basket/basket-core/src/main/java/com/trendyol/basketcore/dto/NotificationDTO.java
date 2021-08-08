package com.trendyol.basketcore.dto;

import com.trendyol.basketcore.models.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO implements Serializable {
    private String id;
    private String userId;
    private NotificationType type;
    private String productId;
}
