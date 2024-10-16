package com.assesment.bookstore.service;

import com.assesment.bookstore.model.CartItem;
import com.assesment.bookstore.model.ShoppingCart;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderSummary {

    private List<CartItem> items;
    private Double totalPrice;

    public OrderSummary(ShoppingCart cart) {
        this.items = cart.getItems();
        this.totalPrice = cart.getTotalPrice();
    }
}

