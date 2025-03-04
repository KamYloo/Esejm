import React from "react";

const PoliticianList = ({ votes }) => {
    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mt-8">
            {votes.length > 0 ? (
                votes.map((vote) => (
                    <div
                        key={vote.id}
                        className="bg-white p-6 rounded-lg shadow-lg flex items-center transition-transform duration-300 hover:scale-105 hover:shadow-2xl cursor-pointer"
                    >
                        <div className="w-24 h-24 rounded-full bg-gray-300 flex-shrink-0 mr-6 overflow-hidden">
                            <img
                                src={vote.mp.photo}
                                alt={`${vote.mp.firstName} ${vote.mp.lastName}`}
                                className="w-full h-full object-cover"
                            />
                        </div>
                        <div className="flex flex-col">
                            <h3 className="text-2xl font-semibold text-gray-800">
                                {vote.mp.firstName} {vote.mp.lastName}
                            </h3>
                            <p className="text-gray-600 text-lg mt-2">Partia: {vote.mp.club}</p>
                            <p
                                className={`text-lg font-bold mt-2 ${
                                    vote.vote === "YES"
                                        ? "text-green-600"
                                        : vote.vote === "NO"
                                            ? "text-red-600"
                                            : "text-yellow-600"
                                }`}
                            >
                                Głos: {vote.vote}
                            </p>
                        </div>
                    </div>
                ))
            ) : (
                <p className="col-span-full text-center text-gray-500">Brak wyników do wyświetlenia</p>
            )}
        </div>
    );
};

export default PoliticianList;
