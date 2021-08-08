package com.trendyol.basketcommand.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;


@Data
public class DeleteBasketCommand {
    @TargetAggregateIdentifier
    private String id;

}
