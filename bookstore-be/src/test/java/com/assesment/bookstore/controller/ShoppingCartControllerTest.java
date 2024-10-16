package com.assesment.bookstore.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.assesment.bookstore.model.ShoppingCart;
import com.assesment.bookstore.service.OrderSummary;
import com.assesment.bookstore.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
    }

    @Test
    public void testGetCart() throws Exception {
        Long userId = 1L;
        ShoppingCart mockCart = new ShoppingCart(); // Create and set up a mock ShoppingCart
        when(shoppingCartService.getCartByUserId(userId)).thenReturn(mockCart);

        mockMvc.perform(get("/api/cart/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddItem() throws Exception {
        Long userId = 1L;
        Long bookId = 1L;
        int quantity = 1;

        ShoppingCart mockCart = new ShoppingCart(); // Create and set up a mock ShoppingCart
        when(shoppingCartService.addItemToCart(userId, bookId, quantity)).thenReturn(mockCart);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", String.valueOf(userId))
                        .param("bookId", String.valueOf(bookId))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRemoveItem() throws Exception {
        Long userId = 1L;
        Long itemId = 1L;

        doNothing().when(shoppingCartService).removeItemFromCart(userId, itemId);

        mockMvc.perform(delete("/api/cart/remove/{userId}/{itemId}", userId, itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCheckoutBadRequest() throws Exception {
        Long userId = 1L;
        when(shoppingCartService.checkout(userId)).thenThrow(new RuntimeException("Checkout failed"));

        mockMvc.perform(post("/api/cart/{userId}", userId))
                .andExpect(status().isBadRequest());
    }
}

