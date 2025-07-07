import { useNavigate } from "react-router-dom"
import { useEffect, useState } from "react";
import { useAuth } from "../functions/AuthContext";

/**
 * Home page component for the Playbook website.
 *
 * - Shows navigation to members (for admins) and playbook.
 * - Uses useAuth to check user roles.
 *
 * @component
 * @returns {JSX.Element} The rendered home page.
 */
export default function Home() {
  const [canSeeMembers, setCanSeeMembers] = useState(false);
  const { hasAnyRole } = useAuth(); 
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
