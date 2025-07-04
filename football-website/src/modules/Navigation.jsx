import "../styles/Navigation.css"

/**
 * Navigation bar component for main app navigation.
 *
 * - Provides links to Home, Login, Logout, and Register pages.
 *
 * @component
 * @returns {JSX.Element} The navigation bar.
 */
export default function Navigation() {
    return (
        <nav>
            <ul>
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    <a href="/login">Login</a>
                </li>
                <li>
                    <a href="/logout">Logout</a>
                </li>
                <li>
                    <a href="/register">Registrieren</a>
                </li>
            </ul>
        </nav>
    )
}
