import React, { useEffect, useState } from 'react';
import { getAllBooks } from '../services/api';
import Book from './Book';

const BookList = () => {
    const [books, setBooks] = useState([]);
    const [cart, setCart] = useState([]);

    // Function to add a book to the cart
    const addToCart = (book) => {
        setCart((prevCart) => {
            // Check if the book is already in the cart
            const existingBook = prevCart.find(item => item.id === book.id);
            if (existingBook) {
                // If it exists, increase the quantity
                return prevCart.map(item => 
                    item.id === book.id 
                    ? { ...item, quantity: item.quantity + 1 }
                    : item
                );
            }
            // If it doesn't exist, add it to the cart
            return [...prevCart, { ...book, quantity: 1 }];
        });
    };

    // Fetch books on component mount
    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const booksFromAPI = await getAllBooks();
                setBooks(booksFromAPI);
            } catch (error) {
                console.error('Error fetching books:', error);
            }
        };

        fetchBooks();
    }, []);

    return (
        <div>
            <h1>Available Books</h1>
            <div className="book-list">
                {books.map((book) => (
                    <Book key={book.id} book={book} addToCart={addToCart} />
                ))}
            </div>
        </div>
    );
};

export default BookList;
