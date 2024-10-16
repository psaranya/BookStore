package com.assesment.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;  // ID of the book added to the cart

    private String title;
    private String author;
    private Double price;

    private int quantity;
}
