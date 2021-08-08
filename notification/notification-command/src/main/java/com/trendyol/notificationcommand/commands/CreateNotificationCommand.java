package com.trendyol.notificationcommand.commands;

import com.trendyol.notificationcore.models.Notification;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;


@Data
@Builder
public class CreateNotificationCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull(message = "Please provide a notification object!")
    private Notification notification;
}
