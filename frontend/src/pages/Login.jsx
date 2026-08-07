import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../services/authService';
import './Login.css';

const Login = () => {

    const [credentials, setCredentials] = useState({
        username: '',
        password: ''
    });

    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const handleChange = (e) => {
        setCredentials({
            ...credentials,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        setError('');
        setLoading(true);

        try {

            const data = await login(credentials);

            console.log("✅ Login Success :", data);
            console.log("✅ Token :", localStorage.getItem("token"));

            // Force browser navigation
            window.location.href = "/dashboard";

        } catch (err) {

            console.error(err);

            setError(
                err.response?.data?.message ||
                "Invalid username or password"
            );

        } finally {

            setLoading(false);

        }
    };

    return (
        <div className="login-container">
            <div className="login-card">

                <h2>TaskFlow AI</h2>
                <p>Employee Recommendation System</p>

                {error &&
                    <div className="alert alert-danger">
                        {error}
                    </div>
                }

                <form onSubmit={handleSubmit}>

                    <div className="form-group">

                        <label htmlFor="username">
                            Username
                        </label>

                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={credentials.username}
                            onChange={handleChange}
                            className="form-control"
                            required
                        />

                    </div>

                    <div className="form-group">

                        <label htmlFor="password">
                            Password
                        </label>

                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={credentials.password}
                            onChange={handleChange}
                            className="form-control"
                            required
                        />

                    </div>

                    <button
                        type="submit"
                        className="btn btn-primary btn-block"
                        disabled={loading}
                    >
                        {loading ? "Logging in..." : "Login"}
                    </button>

                </form>

            </div>
        </div>
    );
};

export default Login;