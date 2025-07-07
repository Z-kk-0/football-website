import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

/**
 * Register page component for new user registration.
 *
 * - Provides a registration form for username, email, and password.
 * - Handles form validation and error display.
 * - Submits registration data to the backend API.
 *
 * @component
 * @returns {JSX.Element} The rendered registration form.
 */
export default function Register() {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: ""
  });
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (form.password !== form.confirmPassword) {
      setError("Passwörter stimmen nicht überein.");
      return;
    }
    try {
      await axios.post(`${API_URL}/auth/signup`, {
        username: form.username,
        email: form.email,
        password: form.password,
      });
      navigate("/login");
    } catch (err) {
      setError(err.response?.data?.message || "Registrierung fehlgeschlagen.");
      console.error(err);
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "40px auto", textAlign: "center" }}>
      <h1>Registrieren</h1>
      <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "1rem" }}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={form.username}
          onChange={handleChange}
          required
        />
        <input
          type="email"
          name="email"
          placeholder="E-Mail"
          value={form.email}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Passwort"
          value={form.password}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="confirmPassword"
          placeholder="Passwort bestätigen"
          value={form.confirmPassword}
          onChange={handleChange}
          required
        />
        <button type="submit">Registrieren</button>
        {error && <p style={{ color: "red" }}>{error}</p>}
      </form>
    </div>
  );
}
