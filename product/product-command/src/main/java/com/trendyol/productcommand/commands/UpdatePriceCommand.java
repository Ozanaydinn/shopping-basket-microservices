package com.trendyol.productcommand.commands;

import com.sun.istack.NotNull;
import com.trendyol.productcore.models.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdatePriceCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull
    private Long newPrice;
}
