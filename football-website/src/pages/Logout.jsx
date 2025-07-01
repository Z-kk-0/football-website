import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import { useAuth } from "../functions/AuthContext";

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
