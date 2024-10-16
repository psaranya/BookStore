import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import BookList from './components/BookList';
import Cart from './components/Cart';
import Checkout from './components/Checkout';
import Login from './components/Login';
import Register from './components/Register';
import { CartProvider } from './context/CartContext'; 
import './styles/App.css';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    const handleLogin = () => {
        setIsAuthenticated(true); // Update authenticated state
    };

    return (
        <CartProvider>
        <Router>
            <div className="app">
                <nav>
                    <ul>
                        {isAuthenticated ? (
                            <>
                                <li><Link to="/">Book List</Link></li>
                                <li><Link to="/cart">Cart</Link></li>
                                <li><Link to="/checkout">Checkout</Link></li>
                                <li><Link to="/logout" onClick={() => setIsAuthenticated(false)}>Logout</Link></li>
                            </>
                        ) : (
                            <li><Link to="/login">Login</Link></li>
                        )}
                    </ul>
                </nav>

                <Routes>
                    <Route path="/" element={<BookList />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path="/checkout" element={<Checkout cartItems={[]} />} />
                    <Route path="/login" element={<Login onLogin={handleLogin} />} />
                </Routes>
            </div>
        </Router>
        </CartProvider>
    );
}

export default App;