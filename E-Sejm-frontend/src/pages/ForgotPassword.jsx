import React, {useState} from "react";
import backgroundImage from '../assets/wwa.jpg';
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {CircularProgress} from "@mui/material";

function ForgotPassword() {

    const [formError, setFormError] = useState(null);
    const [email, setEmail] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        setFormError(null);
        setIsSubmitting(true);

        axios.post("http://localhost:8080/auth/forgot-password", null, {
            params: {
                email: email
            },
        }).then(() =>{
            navigate("/check-mail", {state: {email: email}})
        }).catch(err => {
            setFormError(err?.response?.data?.error);
        }).finally(() => {
            setIsSubmitting(false)
        });
    }

    return (
        <div
            className="h-screen w-full bg-cover bg-center"
            style={{ backgroundImage: `url(${backgroundImage})` }}
        >
            <div className="flex items-center justify-center h-full">
                <div className="bg-white bg-opacity-50 backdrop-blur-md rounded-xl p-10 shadow-lg max-w-4xl flex">
                    <form onSubmit={handleSubmit}>
                        <div className="text-3xl mb-5 font-semibold">
                            Zapomniałem hasła
                        </div>
                        <div className="text-[1rem] text-wrap">
                            Wprowadź adres e-mail, na który chcesz otrzymać informacje<br/> o zresetowaniu hasła
                        </div>
                        <div className="form-group my-7">
                            <label className="text-gray-800">
                                Email
                            </label>
                            <input
                                type="email"
                                name="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                required
                            />
                            {formError && (
                                <p className="text-red-500 text-sm mb-2">{formError}</p>
                            )}
                        </div>
                        <button
                            type="sumbit"
                            className="flex justify-center items-center w-full bg-blue-700 text-white h-10 rounded"
                            disabled={isSubmitting}
                        >
                            {isSubmitting ? (
                                <p className="flex items-center">
                                    <CircularProgress color="#FFFFFF" size={20} className=""/>
                                    <p className="mx-2">Proszę czekać...</p>
                                </p>
                            ): (
                                <p>Zresetuj hasło</p>
                            )}

                        </button>
                        <Link to="/logowanie" className="flex justify-center text-blue-700 mt-5">
                            Wróć do logowania
                        </Link>
                    </form>
                </div>
            </div>
        </div>
    );
}

export { ForgotPassword };