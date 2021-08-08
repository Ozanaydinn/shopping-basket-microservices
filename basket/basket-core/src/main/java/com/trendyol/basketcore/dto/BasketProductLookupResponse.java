package com.trendyol.basketcore.dto;

import com.trendyol.basketcore.models.BasketInfo;
import com.trendyol.basketcore.models.BasketProducts;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class BasketProductLookupResponse {
    private static final double minTotalPrice = 100.0;
    private static final double shippingFee = 4.99;
    private List<BasketProducts> basketProducts;
    private String message;
    private BasketInfo basketInfo = new BasketInfo();

    public BasketProductLookupResponse(List<BasketProducts> basketProducts, String message) {
        this.basketProducts = basketProducts;
        this.message = message;
        calculateBasketInfo();
    }

    public BasketProductLookupResponse(BasketProducts basketProducts, String message) {
        this.basketProducts = new ArrayList<>();
        this.basketProducts.add(basketProducts);
        this.message = message;
        calculateBasketInfo();
    }

    public BasketProductLookupResponse(String message) {
        this.message = message;
        this.basketProducts = null;
        calculateBasketInfo();
    }

    public void calculateBasketInfo() {
        if (this.basketProducts != null && this.basketProducts.size() > 0) {
            var totalPrice = 0.0;
            for (BasketProducts product : this.basketProducts) {
                totalPrice += (product.getProductPrice() * product.getAmount());
            }
            this.basketInfo.setTotalPrice(totalPrice);

            if (totalPrice > minTotalPrice) {
                this.basketInfo.setTotalAmount(totalPrice);
            }
            else {
                this.basketInfo.setTotalAmount(totalPrice + shippingFee);
            }
        }
        else {
            this.basketInfo.setTotalAmount(0);
            this.basketInfo.setTotalPrice(0);
        }
    }

    public List<BasketProducts> getBasketProducts() {
        return basketProducts;
    }

    public void setBasketProducts(List<BasketProducts> basketProducts) {
        this.basketProducts = basketProducts;
    }

    public BasketInfo getBasketInfo() {
        return basketInfo;
    }

    public void setBasketInfo(BasketInfo basketInfo) {
        this.basketInfo = basketInfo;
    }
}
