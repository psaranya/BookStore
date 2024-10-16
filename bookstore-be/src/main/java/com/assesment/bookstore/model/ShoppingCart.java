package com.assesment.bookstore.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Assuming one cart per user; relationship to user entity (if implemented)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> items;

    // Cart total
    private Double totalPrice=0.0;

    public void addItem(CartItem item) {
        items.add(item);
        totalPrice += item.getPrice() * item.getQuantity();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        totalPrice -= item.getPrice() * item.getQuantity();
    }

    // Update total when modifying items
    public void updateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
