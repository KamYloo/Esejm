import React from "react";

const StatisticsSection = () => (
    <div className="bg-white shadow-xl rounded-lg p-6 mb-8 grid grid-cols-1 md:grid-cols-3 gap-8">
        {/* Left: Map or Placeholder */}
        <div className="flex items-center justify-center">
            <div className="w-96 h-96 bg-gray-300 rounded-lg flex items-center justify-center">
                <span className="text-gray-500 text-lg">Zdjęcie / Mapa</span>
            </div>
        </div>

        {/* Right: Statistics */}
        <div className="col-span-2 bg-gradient-to-tl from-blue-400 to-blue-800 text-white p-6 rounded-lg">
            <h2 className="text-2xl font-bold mb-4 text-center">Podlaskie</h2>

            {/* Total Votes */}
            <div className="text-center mb-6">
                <p className="text-5xl font-bold text-blue-200">213,458</p>
                <p className="text-lg font-medium">Liczba wszystkich głosów</p>
                <hr className="my-4 border-blue-300" />
            </div>

            {/* Detailed Statistics */}
            <div className="grid grid-cols-2 gap-4 text-center">
                <div>
                    <p className="text-4xl font-bold text-blue-200">45%</p>
                    <p className="text-sm font-medium">Procent głosów</p>
                    <hr className="mt-4 border-blue-300" />
                </div>
                <div>
                    <p className="text-4xl font-bold text-blue-200">45%</p>
                    <p className="text-sm font-medium">Poparcia</p>
                    <hr className="mt-4 border-blue-300" />
                </div>
                <div>
                    <p className="text-4xl font-bold text-blue-200">45%</p>
                    <p className="text-sm font-medium">Frekwencja</p>
                    <hr className="mt-4 border-blue-300" />
                </div>
                <div>
                    <p className="text-4xl font-bold text-blue-200">14</p>
                    <p className="text-sm font-medium">Liczba mandatów</p>
                    <hr className="mt-4 border-blue-300" />
                </div>
            </div>

            {/* Chart Section */}
            <div className="mt-6 bg-white shadow-md rounded-lg p-4">
                <h3 className="text-lg font-bold text-gray-800 mb-2 text-center">Statystyka: Liczba głosów</h3>
                <div className="h-32 flex items-center justify-center">
                    <span className="text-gray-500">Wykres (Mockup)</span>
                </div>
            </div>
        </div>
    </div>
);

export default StatisticsSection;
