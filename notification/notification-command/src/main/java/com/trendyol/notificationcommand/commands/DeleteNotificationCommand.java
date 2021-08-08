package com.trendyol.notificationcommand.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class DeleteNotificationCommand {
    @TargetAggregateIdentifier
    private String id;
}

