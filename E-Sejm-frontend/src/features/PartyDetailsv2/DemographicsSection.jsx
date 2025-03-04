import React from "react";

const DemographicsSection = () => (
    <div className="bg-gradient-to-tr from-white to-blue-800 text-white shadow-xl rounded-lg p-6 mb-8">
        <h2 className="text-3xl font-bold mb-6 text-center">Dane demograficzne</h2>
        <div className="bg-white shadow-md rounded-lg p-4">
            <h3 className="text-lg font-bold text-gray-800 mb-4 border-b-2 pb-2">Średni wiek wyborców</h3>
            <div className="flex justify-between items-center mb-4">
                <span className="text-sm font-medium text-gray-600">Statystyka</span>
                <div className="flex space-x-2">
                    <span className="text-sm font-medium text-gray-800">Male</span>
                    <span className="text-sm font-medium text-blue-400">Female</span>
                </div>
            </div>
            <div className="space-y-4">
                {[
                    { ageRange: "18-24", male: 3.3, female: 3.3 },
                    { ageRange: "25-34", male: 7.0, female: 5.7 },
                    { ageRange: "35-44", male: 7.8, female: 7.4 },
                    { ageRange: "45-64", male: 13.2, female: 12.1 },
                    { ageRange: "65+", male: 18.0, female: 15.5 },
                ].map((group, index) => (
                    <div key={index} className="flex items-center border-t border-gray-300 pt-2">
                        <span className="w-16 text-sm font-medium text-gray-800">{group.ageRange}</span>
                        <div className="flex-1 flex items-center space-x-2">
                            <div
                                className="h-4 bg-blue-900"
                                style={{ width: `${group.male}%`, maxWidth: "50%" }}
                            ></div>
                            <div
                                className="h-4 bg-blue-400"
                                style={{ width: `${group.female}%`, maxWidth: "50%" }}
                            ></div>
                        </div>
                        <span className="w-16 text-sm font-medium text-gray-800">
                            {(group.male + group.female).toFixed(1)}%
                        </span>
                    </div>
                ))}
            </div>
            <div className="mt-6 border-t border-gray-300 pt-4 flex justify-end">
                <span className="text-xl font-bold text-gray-800">Suma: 31,863</span>
            </div>
        </div>
    </div>
);

export default DemographicsSection;
