import "../styles/Navigation.css"
import { useAuth } from "../functions/AuthContext";
import { useEffect, useState } from "react";    

/**
 * Navigation bar component for main app navigation.
 *
 * - Provides links to Home, Login, Logout, Register, Members, and Plays pages.
 * - Shows only allowed links based on authentication state.
 *
 * @component
 * @returns {JSX.Element} The navigation bar.
 */
export default function Navigation() {
    const { user } = useAuth();
    const { hasAnyRole } = useAuth();
    const [canSeeMembers, setCanSeeMembers] = useState(false);

      useEffect(() => {
        setCanSeeMembers(hasAnyRole(["ROLE_ADMIN"]));
      }, [hasAnyRole]);
    
    return (
        <nav>
            <ul>
                <li>
                    <a href="/">Home</a>
                </li>
                {!user && (
                    <>
                        <li><a href="/login">Login</a></li>
                        <li><a href="/register">Registrieren</a></li>
                    </>
                )}
                {user && (
                    <>
                        {canSeeMembers && <li><a href="/members">Mitglieder</a></li>}
                        <li><a href="/plays">Playbook</a></li>
                        <li><a href="/logout">Logout</a></li>
                    </>
                )}
            </ul>
        </nav>
    )
}
