import { useNavigate } from "react-router-dom"
import { useEffect, useState } from "react";
import AuthService from "../functions/auth.service";


export default function Home() {
    const [canSeeMembers, setCanSeeMembers] = useState(false);

    useEffect(() => {
        setCanSeeMembers(AuthService.hasAnyRole(["ROLE_ADMIN"]));
    }, []);
  let navigate = useNavigate();
  return (
    <>
      <h1>Playbook Website</h1>
      {canSeeMembers && (
        <>
      <button onClick={() => {navigate("/members")}}>
        Mitglieder
      </button>
      </>
)}
      <button onClick={() => {navigate("/plays");}}>
        Playbook
      </button>
    </>
  )
}