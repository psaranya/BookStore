import React, { useEffect, useState } from 'react';
import { getCartItems } from '../services/api'; 

const Cart = () => {
    const [cart, setCart] = useState(null); // Store the entire cart object
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchCartItems = async () => {
            try {
                const userId = localStorage.getItem('userId'); // Retrieve userId from localStorage
                if (!userId) {
                    setError('Please log in to view your cart.');
                    setLoading(false);
                    return;
                }

                const cartData = await getCartItems(userId); // Fetch cart data from backend
                setCart(cartData); // Store the cart object (including items and totalPrice)
            } catch (err) {
                console.error('Error fetching cart items:', err);
                setError('Failed to load cart items.');
            } finally {
                setLoading(false);
            }
        };

        fetchCartItems();
    }, []);

    // Function to handle removing items from the cart (this can be modified if needed)
    const handleRemoveFromCart = (itemId) => {
        setCart((prevCart) => ({
            ...prevCart,
            items: prevCart.items.filter(item => item.id !== itemId),
            totalPrice: prevCart.totalPrice - prevCart.items.find(item => item.id === itemId).price,
        }));
    };

    if (loading) {
        return <p>Loading cart items...</p>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    if (!cart || !cart.items || cart.items.length === 0) {
        return <p>Your cart is empty</p>;
    }

    return (
        <div className="cart">
            <h1>Shopping Cart</h1>
            <div>
                {cart.items.map((item) => (
                    <div key={item.id}>
                        <h3>{item.title}</h3>
                        <p>Author: {item.author}</p>
                        <p>Quantity: {item.quantity}</p>
                        <p>Price: ${item.price}</p>
                        <button onClick={() => handleRemoveFromCart(item.id)}>Remove</button>
                    </div>
                ))}
            </div>
            <h3>Total Price: ${cart.totalPrice.toFixed(2)}</h3> {/* Display total price */}
        </div>
    );
};

export default Cart;
