package com.trendyol.basketcommand.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AddProductToBasketCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull
    private String basketId;

    @NotNull
    private String productId;

    @NotNull
    private String productTitle;

    @NotNull
    private int productStock;

    @NotNull
    private Long productPrice;

    private String imageUrl;

}
