import axios from "axios";
import { useEffect, useState } from "react";
import AuthService from "../functions/auth.service";
import "../Modal.css";

export default function Playbook() {
    const [content, setContent] = useState("");
    const [status, setStatus] = useState(null)
    const [plays, setPlays] = useState([]);
    const [hasCRUDPerms, setHasCRUDPerms] = useState(false);
    const [editingPlay, setEditingPlay] = useState(null);
    const [editContent, setEditContent] = useState("");

    useEffect(() => {
        setHasCRUDPerms(AuthService.hasAnyRole(["ROLE_ADMIN", "ROLE_COACH"]));
    }, []);

    const openEditModal = (play) => {
        setEditingPlay(play)
        setEditContent(play.content)
    };
    
    const getPlays = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/plays", AuthService.getJwtHeader())
            setPlays(res.data)
        }catch (err) {
            console.error("Fehler beim Laden der Plays", err);
            setStatus("fehler beim laden der plays");
            }
        } 
    useEffect(() => {
    getPlays();
    }, [])

    const handleCreate = async () => {
        if (!content.trim()) {
            setStatus("Bitte gib einen play ein")
            return;
        }
        try {
            console.log(content);
            const res = await axios.post("http://localhost:8080/api/plays", {
                content
            },
               AuthService.getJwtHeader() 
            );
            setStatus("play erfolgreich erstellt");
            setContent("");
            await getPlays();
            console.log(res.data);
        } catch (err) {
            console.error(err)
            setStatus("fehler beim absenden")
        }
    };
    const handleDelete = async (id) => {
        try {
         await axios.delete(`http://localhost:8080/api/plays/${id}`, AuthService.getJwtHeader());
         await getPlays();
         setStatus("Play erfolgreich gelöscht");
        } catch (err) {
            console.error("fehler beim löschen", err);
            setStatus("Fehler beim löschen");
        }

    };
    const handleUpdate = async () => {
        if (!editContent.trim()) {
            setStatus("Inhalt darf nicht leer sein");
            return;
        }

        try {
            await axios.put(`http://localhost:8080/api/plays/${editingPlay.id}`,
            {content: editContent},
            AuthService.getJwtHeader());
            setStatus("Play aktualisiert");
            setEditingPlay(null);
            await getPlays();
        } catch (err) {
            console.error("Play konnte nicht Aktualisiert werden ", err)
            setStatus("Fehler beim Aktualisieren des Plays")
        }
    }

    return (
        <>
        <h1>Playbook</h1>
        <ul>
            {plays.length === 0 && <li> keine Plays vorhanden </li>}
            {plays.map((play) => (
                <li key={play.id}>{play.content}
                {hasCRUDPerms && (
                    <>
                      <button
            style={{ marginLeft: "1rem" }}
            onClick={() => handleDelete(play.id)}
      >
        Löschen
      </button>
       <button onClick={() => openEditModal(play)} style={{ marginLeft: "0.5rem" }}>
        Bearbeiten
      </button>
      </>
                )}
                </li>
            ))}
        </ul>
{editingPlay && (
  <div className="modal-overlay">
    <div className="modal">
      <h3>Play bearbeiten</h3>
      <input
        value={editContent}
        onChange={(e) => setEditContent(e.target.value)}
      />
      <div className="modal-buttons">
        <button onClick={() => setEditingPlay(null)}>Abbrechen</button>
        <button onClick={handleUpdate}>Speichern</button>
      </div>
    </div>
  </div>
)}
        {hasCRUDPerms && (
            <>
            <input
            type="text"
            placeholder="z.B. Ace Dart Left"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            /> <br/>
            <button onClick={handleCreate}>
                Play Erstellen
            </button>
                  {status && <p>{status}</p>}
                  </>
        )}
        </>


    )
}