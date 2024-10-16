package com.assesment.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.assesment.bookstore.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(1L);
        // Set any other necessary properties here
        shoppingCartRepository.save(shoppingCart);
    }

    @Test
    public void testFindByUserId() {
        // Act
        ShoppingCart foundCart = shoppingCartRepository.findByUserId(1L);

        // Assert
        assertThat(foundCart).isNotNull();
        assertThat(foundCart.getUserId()).isEqualTo(1L);
    }

    @Test
    public void testFindByUserIdNotFound() {
        ShoppingCart foundCart = shoppingCartRepository.findByUserId(2L);
        assertThat(foundCart).isNull();
    }
}

