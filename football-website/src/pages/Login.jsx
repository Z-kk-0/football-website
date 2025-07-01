import {useNavigate} from 'react-router-dom';
import React, {useState} from 'react';
import {useAuth} from '../functions/AuthContext';

export default function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [entries, setEntries] = useState({ username: "", password: "" });

    function store(e) {
        setEntries({
            ...entries,
            [e.target.name]: e.target.value
        });
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const res = await login(entries.username, entries.password);
            if (res.username) {
                navigate("/");
            }
        } catch (err) {
            if (err?.response?.status === 401) {
                alert("Wrong username or password");
            } else {
                console.error("Login error:", err);
            }
        }
    };

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <h2>Login</h2>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        name="username"
                        value={entries.username}
                        onChange={store}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={entries.password}
                        onChange={store}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
}
