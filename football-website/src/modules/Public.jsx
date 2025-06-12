import {useState} from "react"
import axios from "axios"
import {useEffect} from "react"

export default function Public() {

    const [list, setList] = useState(["Hello World"])
    const [item, setItem] = useState()

    const buttonClicked = (event) => {
        console.log("button clicked: " + event.target.innerText)
    }

    useEffect(() => {
        axios.get("http://localhost:8080/public")
            .then(response => {
                setList(response.data)
            })
    }, [])

    const handleSubmit = async () => {
        try {
            const response = await axios.post('http://localhost:8080/public', {item});
            console.log("post sent")
            setItem("");
        } catch {
            console.error(" error on sending ", error.response?.data || error.message);
        }
    }

    return (

        < div >
            <h1> Public </h1>
            <p> Hurray it works </p>
            <button onClick={buttonClicked}> Button1 </button>
            <p> Neuer Item: </p>
            <input type="text" onChange={(event) =>
                setItem(event.target.value)}
            />
            <button onClick={handleSubmit}>
                Hinzufügen
            </button>
            <ul>
                {list.map((item) => {
                    return <li key={item}>{item}</li>
                })}
            </ul>
        </div>

    )
}

