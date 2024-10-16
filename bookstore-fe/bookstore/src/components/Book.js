import React, { useContext, useState } from 'react';
import { CartContext } from '../context/CartContext'; // Ensure CartContext is properly implemented
import { addToCartApi } from '../services/api'; // API call to backend

const Book = ({ book }) => {
    const { addToCart } = useContext(CartContext); // Using CartContext to update cart state
    const [message, setMessage] = useState(''); // For showing feedback messages

    const handleAddToCart = async () => {
        try {
            const userId = localStorage.getItem('userId'); // Assuming userId is stored after login
            if (!userId) {
                setMessage('Please log in to add items to the cart.');
                return;
            }

            // Call the backend API to add the item to the cart
            await addToCartApi(userId, book.id, 1);

            // Update the cart in local state via CartContext
            addToCart({ ...book, quantity: 1 });

            // Set success message
            setMessage('Item added to cart successfully!');
        } catch (error) {
            console.error('Error adding item to cart:', error);
            setMessage('Failed to add item to cart.');
        }
    };

    return (
        <div className="book">
            <h2>{book.title}</h2>
            <p>Author: {book.author}</p>
            <p>Price: ${book.price}</p>
            <button onClick={handleAddToCart}>Add to Cart</button>
            {message && <p>{message}</p>} {/* Display success/error message */}
        </div>
    );
};

export default Book;
