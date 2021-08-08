package com.trendyol.basketcommand.commands;

import com.trendyol.basketcore.models.Basket;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateBasketCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull
    private Basket basket;
}
