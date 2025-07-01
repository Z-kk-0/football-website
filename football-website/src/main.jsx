import './index.css'
import * as React from "react";
import * as ReactDOM from "react-dom/client";
import {BrowserRouter}
    from "react-router-dom";
import "./index.css";
import App from "./App";
import {AuthProvider} from './functions/AuthContext';

ReactDOM.createRoot(document.getElementById("root"))
    .render(
        <React.StrictMode>
            <AuthProvider>
                <BrowserRouter>
                    <App />
                </BrowserRouter>
            </AuthProvider>
        </React.StrictMode>
    );
