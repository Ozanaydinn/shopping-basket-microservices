package com.trendyol.productcommand.commands;

import com.trendyol.productcore.models.Product;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull
    private Product product;
}
