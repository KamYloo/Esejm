import React, { useState } from "react";

const VotingFilter = ({ onFilter }) => {
    const [sessionNumber, setSessionNumber] = useState("");
    const [votingNumber, setVotingNumber] = useState("");

    const handleFilterSubmit = () => {
        const validSessionNumber = sessionNumber || ""; // Zapewniamy wartość domyślną
        const validVotingNumber = votingNumber || "";

        onFilter(validSessionNumber, validVotingNumber);
    };

    return (
        <div className="mb-6 flex space-x-4">
            <input
                type="number"
                value={sessionNumber}
                placeholder="Numer posiedzenia"
                className="p-2 border border-gray-300 rounded-lg"
                onChange={(e) => setSessionNumber(e.target.value)}
            />
            <input
                type="number"
                value={votingNumber}
                placeholder="Numer głosowania"
                className="p-2 border border-gray-300 rounded-lg"
                onChange={(e) => setVotingNumber(e.target.value)}
            />
            <button
                className="p-2 bg-blue-500 text-white rounded-lg"
                onClick={handleFilterSubmit}
            >
                Filtruj
            </button>
        </div>
    );
};

export default VotingFilter;
