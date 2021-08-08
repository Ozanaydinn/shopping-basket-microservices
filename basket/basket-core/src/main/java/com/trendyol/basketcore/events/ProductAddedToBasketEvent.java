package com.trendyol.basketcore.events;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProductAddedToBasketEvent {
    private String id;

    private String basketId;

    private String productId;

    private String productTitle;

    private int productStock;

    private Long productPrice;

    private String imageUrl;

}
