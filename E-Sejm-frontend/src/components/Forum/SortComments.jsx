import React from "react";

function SortComments({ onSortChange }) {
    const options = ["Data (najnowsze)", "Data (najstarsze)", "Oceny"];

    return (
        <ul className="py-2">
            {options.map((option, index) => (
                <li
                    key={index}
                    className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
                    onClick={() => onSortChange(index)}
                >
                    {option}
                </li>
            ))}
        </ul>
    );
}

export default SortComments;
