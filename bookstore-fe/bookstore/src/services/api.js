import axios from 'axios';

// Set the base URL for your backend API
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // Make sure this matches your backend base URL
});

// Function to get Basic Auth credentials from local storage
const getAuthCredentials = () => {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    console.log(username,password);
    return { username, password };
};

// API call to get all books
export const getAllBooks = async () => {
    try {
        const { username, password } = getAuthCredentials();
        const response = await api.get('/books', {
            auth: {
                username,
                password,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching books:', error);
        throw error;
    }
};

// API call to login
export const loginUser = async (credentials) => {
    try {
        // Send login request to the backend
        const response = await api.post('/auth/login', credentials);
        
        // Assuming the backend now returns the userId as part of the login response
        const { userId } = response.data;

        // Store username, password, and userId in local storage after successful login
        localStorage.setItem('username', credentials.username);
        localStorage.setItem('password', credentials.password);
        localStorage.setItem('userId', userId); // Store the userId in local storage

        return response.data;
    } catch (error) {
        console.error('Login error:', error);
        throw error;
    }
};


// API call to register a user
export const registerUser = async (userData) => {
    try {
        const response = await api.post('/auth/register', userData);
        console.log("id",response.data);
             
        return response.data;
    } catch (error) {
        console.error('Registration error:', error);
        throw error;
    }
};



// API call to fetch cart items (if you store cart on the backend)
export const getCartItems = async (userId) => {
    try {
        const { username, password } = getAuthCredentials();
        const response = await api.get(`/cart/${userId}`, {
            auth: {
                username,
                password,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching cart items:', error);
        throw error;
    }
};


// Remove book from cart
export const removeFromCart = async (userId, bookId) => {
    try {
        const { username, password } = getAuthCredentials();
        const response = await api.delete(`/cart/${userId}/remove/${bookId}`, {
            auth: {
                username,
                password,
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error removing book from cart:', error);
        throw error;
    }
};

// API call to add a book to the cart for a specific user
export const addToCartApi = async (userId, bookId, quantity) => {
    try {
        const { username, password } = getAuthCredentials();

        const response = await api.post(
            `/cart/add`, 
            {}, // This is the request body, if you need to pass anything in the body, you can do it here.
            {
                auth: {
                    username,
                    password,
                },
                params: {
                    userId,
                    bookId,
                    quantity,
                },
            }
        );
  
        console.log(response);
        return response.data;
    } catch (error) {
        console.error('Error adding item to cart:', error);
        throw error;
    }
};

// API call to checkout
export const checkoutCart = async (userId) => {
    try {
        const { username, password } = getAuthCredentials();

        const response = await api.post(`/cart/${userId}`, null, {
            auth: {
                username,
                password,
            },
        });

        return response.data;
    } catch (error) {
        console.error('Error during checkout:', error);
        throw error;
    }
};




