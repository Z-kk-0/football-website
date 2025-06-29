import { useNavigate } from 'react-router-dom';

const NoLogin = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h1>Bitte logge dich ein oder registriere dich</h1>
      <div style={{ marginTop: '20px' }}>
        <button
          onClick={() => navigate('/login')}
          style={{
            margin: '0 10px',
            padding: '10px 20px',
            fontSize: '1rem',
            cursor: 'pointer'
          }}
        >
          Login
        </button>
        <button
          onClick={() => navigate('/register')}
          style={{
            margin: '0 10px',
            padding: '10px 20px',
            fontSize: '1rem',
            cursor: 'pointer'
          }}
        >
          Registrieren
        </button>
      </div>
    </div>
  );
};

export default NoLogin;
