import React from "react";
import { useNavigate } from "react-router-dom";

const VotingList = ({ votings }) => {
    const navigate = useNavigate();

    if (!Array.isArray(votings)) {
        return <div className="text-gray-500">Nie udało się pobrać danych.</div>;
    }

    if (votings.length === 0) {
        return <div className="text-gray-500">Brak wyników do wyświetlenia.</div>;
    }

    return (
        <div className="space-y-6">
            {votings.map((voting) => (
                <div
                    key={voting.id}
                    className={`p-4 rounded-lg cursor-pointer transition-transform transform hover:scale-105 ${
                        voting.yesVotes > voting.noVotes ? "bg-green-500" : "bg-red-500"
                    } text-white`}
                    onClick={() => navigate(`/glosowania/${voting.id}`)} // Przekierowanie
                >
                    <h3 className="font-bold text-lg">{voting.title}</h3>
                    <p className="text-sm">Posiedzenie nr: {voting.meetingId}</p>
                    <p className="text-sm">Głosów "za": {voting.yesVotes}</p>
                    <p className="text-sm">Głosów "przeciw": {voting.noVotes}</p>
                </div>
            ))}
        </div>
    );
};

export default VotingList;
