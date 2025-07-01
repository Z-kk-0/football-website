import Layout from './modules/Layout';
import Home from './pages/Home';
import NoPage from './pages/NoPage';
import {Routes, Route, Navigate} from 'react-router-dom';
import './App.css';
import Login from './pages/Login';
import Logout from './pages/Logout';
import Register from './pages/Register';
import NoLogin from './pages/NoLogin';
import ProtectedRoute from './modules/ProtectedRoute';
import Playbook from './pages/Playbook';
import Members from './pages/Members';

function App() {
    return (
        <Routes>
            <Route path="/" element={<Layout />}>
                <Route path="login" element={<Login />} />
                <Route path="register" element={<Register />} />
                <Route path="nologin" element={<NoLogin />} />

                <Route
                    index
                    element={
                        <ProtectedRoute>
                            <Home />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="logout"
                    element={
                        <ProtectedRoute>
                            <Logout />
                        </ProtectedRoute>
                    }
                />
              <Route
                    path="plays"
                    element={
                        <ProtectedRoute>
                            <Playbook />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="members"
                    element={
                        <ProtectedRoute>
                            <Members />
                        </ProtectedRoute>
                    }
                />
                
                <Route path="*" element={<NoPage />} />
            </Route>
        </Routes>
    );
}

export default App;
