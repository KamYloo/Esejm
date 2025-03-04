import React, { useState, useEffect } from "react";
import axios from "axios";

const UserSettings = () => {
    const [cities, setCities] = useState([]); // Lista miast
    const [selectedCity, setSelectedCity] = useState(""); // Wybrane miasto
    const [error, setError] = useState(""); // Obsługa błędów

    // Pobieranie miast z API
    useEffect(() => {
        const fetchCities = async () => {
            try {
                const response = await axios.post(
                    "https://countriesnow.space/api/v0.1/countries/cities",
                    {
                        country: "Poland",
                    }
                );
                if (response.data && response.data.data) {
                    setCities(response.data.data); // Ustawienie miast
                } else {
                    throw new Error("Nie znaleziono danych miast.");
                }
            } catch (error) {
                console.error("Błąd podczas pobierania miast:", error);
                setError("Nie udało się załadować listy miast.");
            }
        };

        fetchCities();
    }, []);

    return (
        <div className="min-h-screen bg-[#E6E6E6] flex items-center justify-center">
            <div className="container mx-auto p-6 bg-white rounded-lg shadow-lg max-w-4xl">
                <h1 className="text-3xl font-bold text-[#1D3461] text-center mb-8">Ustawienia Profilu</h1>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {/* Lokalizacja */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow">
                        <label className="block text-white text-lg font-bold mb-2">Lokalizacja:</label>
                        {error ? (
                            <p className="text-red-500">{error}</p>
                        ) : (
                            <select
                                className="w-full p-2 rounded-lg bg-white border border-gray-300"
                                value={selectedCity}
                                onChange={(e) => setSelectedCity(e.target.value)}
                            >
                                <option value="">Wybierz miasto</option>
                                {cities.map((city, index) => (
                                    <option key={index} value={city}>
                                        {city}
                                    </option>
                                ))}
                            </select>
                        )}
                    </div>

                    {/* Data Urodzenia */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow">
                        <label className="block text-white text-lg font-bold mb-2">Data Urodzenia:</label>
                        <input
                            type="date"
                            className="w-full p-2 rounded-lg bg-white border border-gray-300"
                            onChange={(e) => console.log("Wybrana data:", e.target.value)}
                        />
                    </div>

                    {/* Imię */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow">
                        <label className="block text-white text-lg font-bold mb-2">Imię:</label>
                        <input
                            type="text"
                            defaultValue="Jan"
                            className="w-full p-2 rounded-lg bg-white border border-gray-300"
                        />
                    </div>

                    {/* Nazwisko */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow">
                        <label className="block text-white text-lg font-bold mb-2">Nazwisko:</label>
                        <input
                            type="text"
                            defaultValue="Borownik"
                            className="w-full p-2 rounded-lg bg-white border border-gray-300"
                        />
                    </div>

                    {/* Zdjęcie profilowe */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow">
                        <label className="block text-white text-lg font-bold mb-2">Zdjęcie profilowe:</label>
                        <div className="flex items-center gap-2">
                            <input
                                type="file"
                                className="flex-1 p-2 rounded-lg bg-white border border-gray-300"
                            />
                            <img
                                src="https://wykop.pl/cdn/c3201142/comment_1591615839nbGTtqdTIomrzADM8oC0rW.jpg"
                                alt="User Avatar"
                                className="w-16 h-16 rounded-full shadow"
                            />
                        </div>
                    </div>

                    {/* Płeć */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow">
                        <label className="block text-white text-lg font-bold mb-2">Płeć:</label>
                        <select className="w-full p-2 rounded-lg bg-white border border-gray-300">
                            <option>Mężczyzna</option>
                            <option>Kobieta</option>
                            <option>Inna</option>
                        </select>
                    </div>

                    {/* Ustawienia prywatności */}
                    <div className="bg-[#608FC7] p-4 rounded-lg shadow col-span-2">
                        <label className="block text-white text-lg font-bold mb-2">Ustawienia prywatności:</label>
                        <div className="space-y-2">
                            <label className="flex items-center gap-2 text-white">
                                <input type="checkbox" defaultChecked className="form-checkbox text-[#1F487E]" />
                                Ukryć Imię?
                            </label>
                            <label className="flex items-center gap-2 text-white">
                                <input type="checkbox" defaultChecked className="form-checkbox text-[#1F487E]" />
                                Ukryć Nazwisko?
                            </label>
                            <label className="flex items-center gap-2 text-white">
                                <input type="checkbox" className="form-checkbox text-[#1F487E]" />
                                Ukryć Lokalizację?
                            </label>
                            <label className="flex items-center gap-2 text-white">
                                <input type="checkbox" defaultChecked className="form-checkbox text-[#1F487E]" />
                                Ukryć Datę Urodzenia?
                            </label>
                        </div>
                    </div>
                </div>

                {/* Aktualizuj Profil */}
                <button className="bg-[#1F487E] text-white py-3 px-6 rounded-lg mt-6 block mx-auto hover:bg-[#1D3461] transition">
                    Aktualizuj Profil
                </button>
            </div>
        </div>
    );
};

export default UserSettings;
