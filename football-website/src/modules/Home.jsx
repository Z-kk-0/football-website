import { useNavigate } from "react-router-dom"

export default function Home() {
  let navigate = useNavigate();
  return (
    <>
      <h1>Playbook Website</h1>
      <button onClick={() => {navigate("/members")}}>
        Mitglieder
      </button>
      <button onClick={() => {navigate("/plays");}}>
        Playbook
      </button>
    </>
  )
}