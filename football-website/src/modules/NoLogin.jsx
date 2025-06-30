import { useNavigate } from 'react-router-dom';

const NoLogin = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h1>Bitte logge dich ein oder registriere dich</h1>
      <div style={{ marginTop: '20px' }}>
        <button
          onClick={() => navigate('/login')}
        >
          Login
        </button>
        <button
          onClick={() => navigate('/register')}
        >
          Registrieren
        </button>
      </div>
    </div>
  );
};

export default NoLogin;
