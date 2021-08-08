package com.trendyol.basketcore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketInfo implements Serializable {
    private static final double shippingFee = 4.99;

    // total price of the products that are in the basket
    private double totalPrice;

    // total amount if fee is applicable
    private double totalAmount;

    public static double getShippingFee() {
        return shippingFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
