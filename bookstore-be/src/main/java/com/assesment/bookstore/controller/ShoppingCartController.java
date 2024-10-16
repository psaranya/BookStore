package com.assesment.bookstore.controller;

import com.assesment.bookstore.model.ShoppingCart;
import com.assesment.bookstore.service.OrderSummary;
import com.assesment.bookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingCartService.getCartByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> addItem(@RequestParam Long userId,
                                                @RequestParam Long bookId,
                                                @RequestParam int quantity) {
        return ResponseEntity.ok(shoppingCartService.addItemToCart(userId, bookId, quantity));
    }

    @DeleteMapping("/remove/{userId}/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long userId, @PathVariable Long itemId) {
        shoppingCartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderSummary> checkout(@PathVariable Long userId) {
        try {
            OrderSummary summary = shoppingCartService.checkout(userId);
            return ResponseEntity.ok(summary);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
