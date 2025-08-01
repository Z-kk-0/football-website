/**
 * AuthContext provides authentication state and helpers for the app.
 *
 * - Stores user info and JWT in localStorage.
 * - Provides login, logout, and role-checking utilities.
 * - Used via useAuth() hook throughout the app.
 *
 * @context
 */
import { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";

const AuthContext = createContext();
const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(() => {
        const stored = localStorage.getItem("user");
        return stored ? JSON.parse(stored) : null;
    });

    useEffect(() => {
        if (user) {
            localStorage.setItem("user", JSON.stringify(user));
        } else {
            localStorage.removeItem("user");
        }
    }, [user]);

    const login = async (username, password) => {
        try {
            const res = await axios.post(`${API_URL}/auth/login`, { username, password });
            if (res.data?.username) {
                setUser(res.data);
                return res.data;
            }
        } catch (err) {
            console.error("Login failed:", err);
            throw err;
        }
    };

    const logout = () => {
        setUser(null);
    };

    const getJwtHeader = () => {
        return user?.token
            ? { headers: { Authorization: `Bearer ${user.token}` } }
            : {};
    };

    const getRole = () => {
        return user?.roles?.[0] || user?.role || null;
    };

    const hasAnyRole = (targetRoles) => {
        const rawRoles = user?.roles;
        const roles = Array.isArray(rawRoles)
            ? typeof rawRoles[0] === "string"
                ? rawRoles
                : rawRoles.map((r) => r.authority)
            : [];
        return targetRoles.some((role) => roles.includes(role));
    };

    return (
        <AuthContext.Provider value={{ user, login, logout, getJwtHeader, getRole, hasAnyRole }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);
