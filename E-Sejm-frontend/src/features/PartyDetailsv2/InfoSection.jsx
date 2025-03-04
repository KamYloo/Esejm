import React from "react";
import worldBackground from "../../assets/world_background.jpg"; // Import obrazu

const InfoSection = () => (
    <div
        className="shadow-xl text-white p-6 rounded-lg mb-8"
        style={{
            backgroundImage: `url(${worldBackground})`, // Ustawienie obrazu jako tła
            backgroundSize: "cover", // Dopasowanie obrazu do rozmiaru kontenera
            backgroundPosition: "center", // Wycentrowanie obrazu
        }}
    >
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 bg-black bg-opacity-50 p-4 rounded-lg">
            {/* Left Side Info */}
            <div className="text-center md:col-span-2 space-y-4">
                <div className="border-b border-gray-300 pb-2">
                    <p className="text-lg">
                        <span className="text-4xl text-blue-400 font-bold">18 Członków</span>
                    </p>
                </div>
                <p className="text-lg">
                    Data założenia: <span className="font-bold text-blue-200">6 grudnia 2018</span>
                </p>
                <p className="text-lg">
                    Partie członkowskie: <span className="font-bold text-blue-200">NN, RN, KKP, Ruch PE, Konserwatyści</span>
                </p>
                <p className="text-lg">
                    Adres siedziby: <span className="font-bold text-blue-200">ul. Piękna 24/26A, 00-549 Warszawa</span>
                </p>
            </div>
            {/* Right Side Image */}
            <div className="flex items-center justify-center">
                <div className="w-96 h-48 bg-gray-200 rounded-lg flex items-center justify-center">
                    <img
                        src="https://klubjagiellonski.pl/wp-content/uploads/2023/10/pigulka-konfa-720x375.jpg"
                        alt="Zdjęcie partii"
                        className="object-cover rounded-lg"
                    />
                </div>
            </div>
        </div>
    </div>
);

export default InfoSection;
