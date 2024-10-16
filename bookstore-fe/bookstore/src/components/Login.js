import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom'; 
import { loginUser } from '../services/api'; // Ensure the path matches your folder structure
 
const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        try {
            // Call the login API
            await loginUser({ username, password });
            
            // If login is successful, store credentials and call onLogin callback
            localStorage.setItem('username', username);
            localStorage.setItem('password', password);
            onLogin(); // Call the onLogin function to update parent state
            navigate('/'); // Redirect to the BookList after login
        } catch (error) {
            setErrorMessage('Invalid username or password'); // Show error message
            console.error('Login error:', error);
        }
    };

    return (
        <div className="container">
           
            {errorMessage && <p className="error">{errorMessage}</p>} 
            <form onSubmit={handleLoginSubmit}>
                <div>
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            <p>
                Don't have an account? <Link to="/register">Register here</Link>
            </p>
        </div>
    );
};

export default Login;