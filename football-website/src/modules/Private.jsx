import axios from "axios"
import {useEffect, useState} from "react"
import AuthService from "../functions/auth.service";

export default function Private() {
    const [response, setResponse] = useState()

    useEffect(() => {
        axios.get("http://localhost:8080/private", AuthService.getJwtHeader())
            .then(resp => {
                setResponse(resp.data)
            })
    }, []);
    return (

        < div >
            <h1> Private </h1>
            <p> Hurray it works </p>
            <p> {response} </p>
        </div>

    )
}

