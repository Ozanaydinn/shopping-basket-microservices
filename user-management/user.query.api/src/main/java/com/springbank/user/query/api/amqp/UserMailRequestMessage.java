package com.springbank.user.query.api.amqp;

import com.springbank.user.query.api.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMailRequestMessage implements Serializable {
    private String id;
    private String email;
    private NotificationDTO notification;
}
