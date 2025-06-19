import {redirect} from "react-router-dom";
import AuthService from "../functions/auth.service";

const delay = ms => new Promise(res => setTimeout(res, ms));

export default function Logout() {
    AuthService.logout();
    delay(500).then(() => {redirect("/");});
}
