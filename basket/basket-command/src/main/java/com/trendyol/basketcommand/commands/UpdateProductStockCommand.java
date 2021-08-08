package com.trendyol.basketcommand.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateProductStockCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull
    private String productId;

    @NotNull
    private int newStock;
}
