import React, { useState } from 'react';
import { checkoutCart } from '../services/api'; 

const Checkout = ({ cartItems }) => {
    const [checkoutMessage, setCheckoutMessage] = useState('');
    const [totalPrice, setTotalPrice] = useState('');
   
    const handleCheckout = async () => {
        const userId = localStorage.getItem('userId');
        try {
            const orderSummary = await checkoutCart(userId);

            console.log('Checkout successful:', orderSummary.totalPrice);
            setTotalPrice(orderSummary.totalPrice);
            setCheckoutMessage(`Order placed successfully! Total price is $${orderSummary.totalPrice}.`);
            // Optionally, you can redirect to a confirmation page or reset the cart
        } catch (error) {
            console.error('Checkout failed:', error);
            setCheckoutMessage('Checkout failed. Please try again.');
            // Handle error (e.g., show error message to user)
        }
    };

    return (
        <div className="checkout">
            <h1>Order Summary</h1>
            
            <button onClick={handleCheckout}>Checkout</button>
            {checkoutMessage && <p>{checkoutMessage}</p>} {/* Display the checkout message */}
        </div>
    );
};

export default Checkout;
