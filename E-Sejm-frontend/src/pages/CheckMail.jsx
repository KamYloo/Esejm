import React from 'react';
import backgroundImage from "../assets/wwa.jpg";
import {Link, useLocation} from "react-router-dom";

export function CheckMail() {

    const location = useLocation()

    return (
        <div
            className="h-screen w-full bg-cover bg-center"
            style={{backgroundImage: `url(${backgroundImage})`}}
        >
            <div className="flex items-center justify-center h-full">
                <div className="bg-white bg-opacity-50 backdrop-blur-md rounded-xl p-10 shadow-lg max-w-4xl flex flex-col">
                    <div className="text-3xl mb-5 font-semibold flex justify-center">
                        Sprawdź email
                    </div>
                    <div>
                        Wysłaliśmy wiadomość na adres {location?.state?.email}.<br/> Podążaj za instrukcją żeby zresetować hasło.
                    </div>
                    <Link to="/logowanie" className="flex justify-center text-blue-700 mt-5">
                        Wróć do logowania
                    </Link>
                </div>
            </div>
        </div>
    );
}