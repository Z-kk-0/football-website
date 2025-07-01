import {useState, useEffect} from "react";
import axios from "axios";
import AuthService from "../functions/auth.service";
import "../index.css";



export default function Members() {
    const [users, setUsers] = useState([]);
    const [status, setStatus] = useState(null);

    const getUsers = async () => {
        try {
            const res = await axios.get(
                "http://localhost:8080/api/users",
                AuthService.getJwtHeader()
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

    return (
        <>
            <h1>Mitglieder</h1>
            {status && <p>{status}</p>}
            <ul>
                {users.length === 0 && <li>Keine User vorhanden</li>}
                {users.map((user) => (
                    <li key={user.id}>
                        <div>
                            <strong>{user.username}</strong> – {user.email}
                        </div>
                        {console.log("🧾 ROLES:", user.role)}
                        <div>
                            <span className="role-badge">{user.role || "keine Rolle"}</span>                                </span>
                            ))}
                    </div>
                    </li>
                ))}
        </ul >
        </>
    );
}

