package com.trendyol.notificationcommand.amqp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trendyol.notificationcore.dto.NotificationDTO;
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
public class CreateNotificationMessage implements Serializable {
    @JsonProperty("id")
    private String id;


    @JsonProperty("notification")
    private NotificationDTO notification;
}
