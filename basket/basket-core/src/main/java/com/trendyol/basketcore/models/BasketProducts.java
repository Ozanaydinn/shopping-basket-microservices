package com.trendyol.basketcore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketProducts {
    @Id
    private String id;

    @Column(name = "basket_id")
    @NotNull
    private String basketId;

    @Column(name = "productId")
    @NotNull
    private String productId;

    @Column(name = "product_title")
    @NotNull
    private String productTitle;

    @Column(name = "product_stock")
    @NotNull
    private int productStock;

    @Column(name = "product_price")
    @NotNull
    private Long productPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "amount")
    private int amount;

}
