package com.assesment.bookstore.service;
import com.assesment.bookstore.model.Book;
import com.assesment.bookstore.model.CartItem;
import com.assesment.bookstore.model.ShoppingCart;
import com.assesment.bookstore.repository.BookRepository;
import com.assesment.bookstore.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private BookRepository bookRepository;

    public ShoppingCart getCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    @Transactional
    public ShoppingCart addItemToCart(Long userId, Long bookId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUserId(userId);
        }

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        // Find the book to add to the cart
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if the item already exists in the cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);  // Update quantity
        } else {
            CartItem newItem = CartItem.builder()
                    .bookId(bookId)
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .price(book.getPrice())
                    .quantity(quantity)
                    .build();
            cart.addItem(newItem);
        }

        cart.updateTotalPrice();
        return shoppingCartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long userId, Long itemId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null) throw new RuntimeException("Cart not found");

        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        cart.updateTotalPrice();
        shoppingCartRepository.save(cart);
    }

    // Proceed to checkout and return summary
    @Transactional
    public OrderSummary checkout(Long userId) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Proceed to checkout (this can involve payment, etc. in real scenario)
        OrderSummary summary = new OrderSummary(cart);

        // Clear the cart after checkout
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        shoppingCartRepository.save(cart);

        return summary;
    }
}
