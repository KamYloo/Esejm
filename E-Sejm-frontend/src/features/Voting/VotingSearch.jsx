import React, { useState } from "react";

const VotingSearch = ({ onSearch }) => {
    const [searchTerm, setSearchTerm] = useState("");

    const handleSearch = (e) => {
        const value = e.target.value;
        setSearchTerm(value);

        // Debounce: Odczekuje 500ms przed wywołaniem funkcji wyszukiwania
        setTimeout(() => {
            onSearch(value);
        }, 500);
    };

    return (
        <div className="mb-4">
            <input
                type="text"
                placeholder="Wyszukaj głosowanie..."
                value={searchTerm}
                className="w-full p-2 border border-gray-300 rounded-lg"
                onChange={handleSearch}
            />
        </div>
    );
};

export default VotingSearch;
