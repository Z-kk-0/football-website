import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import { useAuth } from "../functions/AuthContext";

/**
 * Logout page component.
 *
 * - Logs out the user and redirects to the home page after a short delay.
 * - Uses useAuth for logout and useNavigate for redirection.
 *
 * @component
 * @returns {null} No rendered output.
 */
export default function Logout() {
    const navigate = useNavigate();
    const {logout} = useAuth();
    useEffect(() => {
        logout();
        setTimeout(() => {
            navigate("/", {replace: true});
        }, 500);
    }, [logout, navigate]);

    return null;
}
