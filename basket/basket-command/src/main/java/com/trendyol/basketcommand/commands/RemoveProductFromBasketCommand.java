package com.trendyol.basketcommand.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
public class RemoveProductFromBasketCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull
    private String basketId;

    @NotNull
    private String productId;
}
