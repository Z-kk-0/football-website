import { useState, useEffect } from "react";
import axios from "axios";
import { useAuth } from "../functions/AuthContext";
import "../index.css";

/**
 * Members page component for managing user roles.
 *
 * - Fetches and displays all users.
 * - Allows updating user roles (admin, coach, player) via dropdown.
 * - Only accessible to authorized users.
 *
 * @component
 * @returns {JSX.Element} The rendered members management page.
 */

export default function Members() {
    const { getJwtHeader } = useAuth(); 

    const [users, setUsers] = useState([]);
    const [status, setStatus] = useState(null);
    const availableRoles = ["ROLE_ADMIN", "ROLE_COACH", "ROLE_PLAYER"];

    const getUsers = async () => {
        try {
            const res = await axios.get(
                "http://localhost:8080/api/users",
                getJwtHeader()
            );
            setUsers(res.data);
        } catch (err) {
            console.error("Fehler beim Laden der User", err);
            setStatus("Fehler beim Laden der User");
        }
    };

    useEffect(() => {
        getUsers();
    }, []);

    const updateUserRole = async (userId, newRole) => {
        try {
            await axios.put(
                `http://localhost:8080/api/users/${userId}/role`,
                { roleName: newRole },
                getJwtHeader()
            );
            setStatus("Rolle aktualisiert");
            await getUsers();
        } catch (err) {
            console.error("Fehler beim Aktualisieren der Rolle", err);
            setStatus("Fehler beim Aktualisieren der Rolle");
        }
    };

    return (
        <>
            <h1>Mitglieder</h1>
            <ul>
                {users.length === 0 && <li>Keine User vorhanden</li>}
                {users.map((user) => (
                    <li key={user.id}>
                        <div>
                            <strong>{user.username}</strong> -- {user.email}
                            <select
                                value={user.role}
                                onChange={(e) => updateUserRole(user.id, e.target.value)}
                                className="role-select"
                            >
                                {availableRoles.map((role) => (
                                    <option key={role} value={role}>
                                        {role.replace("ROLE_", "")}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </li>
                ))}
            </ul>
            {status && <p>{status}</p>}
        </>
    );
}
