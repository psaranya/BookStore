import React, { createContext, useState } from 'react';

// Create a context for the cart
export const CartContext = createContext();

export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);

    // Function to add an item to the cart
    const addToCart = (book) => {
        const existingItem = cartItems.find(item => item.id === book.id);
        if (existingItem) {
            setCartItems(cartItems.map(item =>
                item.id === book.id ? { ...item, quantity: item.quantity + 1 } : item
            ));
        } else {
            setCartItems([...cartItems, { ...book, quantity: 1 }]);
        }
    };

    // Cart context value
    const value = {
        cartItems,
        addToCart,
    };

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
};
