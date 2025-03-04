import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import backgroundImage from '../assets/wwa.jpg';
import logoEsejm from '../assets/logoEsejm.png';
import {registerAction} from "../redux/AuthService/Action.js";
import toast from "react-hot-toast";
import {useDispatch} from "react-redux";

function Register() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [formData, setFormData] = useState({
        nickName: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
        role: ''
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (formData.password !== formData.confirmPassword) {
            alert('Hasło i Powtórz hasło muszą być identyczne.');
            return;
        }
        const { confirmPassword, ...dataToSend } = formData;

        dispatch(registerAction(dataToSend))
            .then(() => {
                navigate("/logowanie");
                toast.success("You have registered successfully.");
            })
            .catch(() => {
                toast.error("Failed to register. Please try again.");
            })
    };

    return (
        <div
            className="min-h-screen w-full bg-cover bg-center"
            style={{ backgroundImage: `url(${backgroundImage})` }}
        >
            <div className="flex items-center justify-center h-full p-10">
                <div className="bg-white bg-opacity-50 backdrop-blur-md rounded-xl p-8 shadow-lg max-w-4xl w-1/4 flex flex-col">

                    <div className="flex justify-center mb-4">
                        <img src={logoEsejm} alt="Logo E-Sejm" className="h-12 w-auto" />
                    </div>
                    
                    <h1 className="text-gray-800 text-xl font-semibold mb-4 mt-5 text-center">Rejestracja</h1>

                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label className="text-gray-800">Nick</label>
                            <input
                                type="text"
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                name="nickName"
                                value={formData.nickName}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label className="text-gray-800">Imię</label>
                            <input
                                type="text"
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label className="text-gray-800">Nazwisko</label>
                            <input
                                type="text"
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="mt-3">
                            <label className="text-gray-800">Email</label>
                            <input
                                type="email"
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                name="email"
                                value={formData.email}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="mt-3">
                            <label className="text-gray-800">Hasło</label>
                            <input
                                type="password"
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                name="password"
                                value={formData.password}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="mt-3">
                            <label className="text-gray-800">Powtórz hasło</label>
                            <input
                                type="password"
                                className="w-full p-2 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-700"
                                name="confirmPassword"
                                value={formData.confirmPassword}
                                onChange={handleInputChange}
                                required
                            />
                        </div>

                        <div className="bg-white rounded-lg mt-7 p-4 flex items-center justify-center shadow-md">
                            Captcha
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-blue-700 text-white py-2 rounded-lg mt-7 hover:bg-blue-600 transition-colors"
                        >
                            Zarejestruj się
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export {Register};
