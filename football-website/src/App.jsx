import Layout from './modules/Layout'
import Home from './modules/Home'
import About from './modules/About'
import NoPage from './modules/NoPage'
import {Routes} from 'react-router-dom'
import {Route} from 'react-router-dom'
import './App.css'
import Public from './modules/Public'
import Login from './modules/Login'
import Private from './modules/Private'
import Logout from './modules/Logout'
import Register from './modules/Register'
import NoLogin from './modules/NoLogin'

function App() {
    return (
        <Routes>
            <Route path="/" element={<Layout />}>
                <Route index element={<Home />} />
                <Route path="public" element={<Public />} />
                <Route path="about" element={<About />} />
                <Route path="*" element={<NoPage />} />
                <Route path="login" element={<Login />} />
                <Route path="private" element={< Private />} />
                <Route path="logout" element={<Logout />} />
                <Route path="register" element={<Register />} />
                <Route path="nologin" element={<NoLogin />} />
            </Route>
        </Routes>
    )

}

export default App
