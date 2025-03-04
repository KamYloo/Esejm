import React, {useState} from "react";
import backgroundImage from '../assets/wwa.jpg';
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";

function ChangePassword() {
    const [formPasswordError, setFormPasswordError] = useState(null);
    const [formConfirmPasswordError, setFormConfirmPasswordError] = useState(null);
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const navigate = useNavigate();
    const reset_password_url = "http://localhost:8080/auth/reset-password";
    const { userID, token } = useParams()

    const handleSubmit = (event) => {
        event.preventDefault();
        if(password.length < 8){
            setFormPasswordError("Password should be 8 characters long min");
            return;
        }
        if(confirmPassword.length < 8){
            setFormConfirmPasswordError("Password confirmation should be 8 characters long min");
            return;
        }
        if(password !== confirmPassword){
            setFormConfirmPasswordError("Password not match");
            return;
        }
        axios.post(`${reset_password_url}/${userID}/${token}`,
            {
                password: password,
                passwordConfirmation: confirmPassword
            }, {

        }).then(() =>{
            navigate("/logowanie")
        })
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
                                Nowe hasło
                            </label>
                            <input
                                type="password"
                                name="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                required
                            />
                            {formPasswordError && (
                                <p className="text-red-500 text-sm mb-2">{formPasswordError}</p>
                            )}
                        </div>
                        <div className="form-group my-7">
                            <label className="text-gray-800">
                                Potwierdź nowe hasło
                            </label>
                            <input
                                type="password"
                                name="confirmPassword"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                required
                            />
                            {formConfirmPasswordError && (
                                <p className="text-red-500 text-sm mb-2">{formConfirmPasswordError}</p>
                            )}
                        </div>
                        <button
                            type="sumbit"
                            className="flex justify-center items-center w-full bg-blue-700 text-white h-10 rounded"
                        >
                            Zmień hasło
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export {ChangePassword};