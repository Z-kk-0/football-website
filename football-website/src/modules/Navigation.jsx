import "../styles/Navigation.css"

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
