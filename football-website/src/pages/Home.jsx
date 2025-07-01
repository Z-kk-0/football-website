import { useNavigate } from "react-router-dom"
import { useEffect, useState } from "react";
import { useAuth } from "../functions/AuthContext";

export default function Home() {
  const [canSeeMembers, setCanSeeMembers] = useState(false);
  const { hasAnyRole } = useAuth(); // ✅ Hook korrekt aufgerufen
  const navigate = useNavigate();

  useEffect(() => {
    setCanSeeMembers(hasAnyRole(["ROLE_ADMIN"]));
  }, [hasAnyRole]);

  return (
    <>
      <h1>Playbook Website</h1>

      {canSeeMembers && (
        <button onClick={() => navigate("/members")}>
          Mitglieder
        </button>
      )}

      <button onClick={() => navigate("/plays")}>
        Playbook
      </button>
    </>
  );
}
