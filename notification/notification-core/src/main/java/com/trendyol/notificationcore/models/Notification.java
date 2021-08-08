package com.trendyol.notificationcore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification implements Serializable {
    @Id
    private String id;

    @NotEmpty
    private NotificationType notificationType;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String productId;

}
