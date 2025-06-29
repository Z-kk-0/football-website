import {useNavigate} from 'react-router-dom';
import AuthService from '../functions/auth.service';
import React, {useState} from 'react';

export default function Login() {
    const navigate = useNavigate();
    const [entries, setEntries] = useState({username: "", password: ""})
    function store(e) {
        setEntries({
            ...entries,
            [e.target.name]: e.target.value
        });
    }
    const handleSubmit = (event) => {
        event.preventDefault();
        try {
            AuthService.login(entries.username, entries.password)
                .then((res) => {
                    if (res.username) {
                    navigate("/");
                    }
                })
                .catch((err) => {
                    if (err.status === 401) {
                        alert("Wrong username or password");
                    }
                });
        } catch (err) {console.log(err);}
    };

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <h2>Login</h2>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input type="text" id="username"
                        name="username" value={entries.username}
                        onChange={store} required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input type="password" id="password"
                        name="password" value={entries.password}
                        onChange={store} required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
}
