import React from "react";
import { FaSearch } from "react-icons/fa";

function SearchBar({ searchTerm, setSearchTerm }) {
    return (
        <div className="relative items-center">
            <input
                type="text"
                placeholder="Szukaj posła..."
                className="w-full px-6 py-4 text-lg rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 shadow-md"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <FaSearch className="absolute right-4 top-5 text-gray-500 text-lg" />
        </div>
    );
}

export {SearchBar};
