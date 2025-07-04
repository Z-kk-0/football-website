import {Navigate} from 'react-router-dom';
import {useAuth} from '../functions/AuthContext';

/**
 * ProtectedRoute component to guard routes that require authentication.
 *
 * - Redirects unauthenticated users to /nologin.
 * - Renders children if user is authenticated.
 *
 * @component
 * @param {object} props
 * @param {React.ReactNode} props.children - The protected content.
 * @returns {JSX.Element|null} The protected content or redirect.
 */
export default function ProtectedRoute({children}) {
    const {user} = useAuth();

    if (!user) {
        return <Navigate to="/nologin" replace />;
    }

    return children;
}
