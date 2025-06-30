import axios from "axios";

const login = (username, password) => {
    return axios
        .post('http://localhost:8080/api/auth/login',
            {username, password, })
        .then((response) => {
            if (response.data.username) {
                localStorage.setItem("user",
                    JSON.stringify(response.data));
                return response.data;
            }
        }).catch((error) => {
            console.log(error);
            throw error;
        })
};

const logout = () => {
    localStorage.removeItem("user");
};
const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};
const getJwtHeader = () => {
    let tok = JSON.parse(localStorage.getItem("user")).token
    return {headers: {Authorization: `Bearer ${tok}`}}
}

const getRole = () => {
    const user = JSON.parse(localStorage.getItem("user"));
    return user?.roles?.[0] || user?.role || null;
};

const hasAnyRole = (targetRoles) => {
  const user = getCurrentUser();
  const rawRoles = user?.roles || [];

  const roles = Array.isArray(rawRoles)
    ? typeof rawRoles[0] === "string"
      ? rawRoles
      : rawRoles.map((r) => r.authority)
    : [];

  return targetRoles.some(role => roles.includes(role));
};

const AuthService = {
    login,
    logout,
    getJwtHeader,
    getCurrentUser,
    getRole,
    hasAnyRole
}
export default AuthService;
