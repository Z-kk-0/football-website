import "../styles/Navigation.css"

export default function Navigation() {
    return (
        <nav>
            <ul>
                <li>
                    <a href="/">Home</a>
                </li>
                <li>
                    <a href="/about">About</a>
                    <a href="/public">Public</a>
                    <a href="/login">Login</a>
                    <a href="/private">Private</a>
                </li>
            </ul>
        </nav>
    )
}
