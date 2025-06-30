import Layout from './modules/Layout';
import Home from './modules/Home';
import About from './modules/About';
import NoPage from './modules/NoPage';
import {Routes, Route, Navigate} from 'react-router-dom';
import './App.css';
import Public from './modules/Public';
import Login from './modules/Login';
import Private from './modules/Private';
import Logout from './modules/Logout';
import Register from './modules/Register';
import NoLogin from './modules/NoLogin';
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
                    path="about"
                    element={
                        <ProtectedRoute>
                            <About />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="public"
                    element={
                        <ProtectedRoute>
                            <Public />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="private"
                    element={
                        <ProtectedRoute>
                            <Private />
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
