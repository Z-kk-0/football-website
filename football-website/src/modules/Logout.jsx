import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import AuthService from "../functions/auth.service";
import {useAuth} from "./AuthContext";

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
