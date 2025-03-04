import React from 'react';

function Thread({ title, category, date, comments, score }) {
    // Funkcja formatowania daty
    const formatDate = (isoDate) => {
        if (!isoDate) return "Brak daty";
        const date = new Date(isoDate);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Miesiące od 0 do 11
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
    };

    return (
        <div className="bg-blue-100 border border-blue-300 rounded-lg p-4 shadow-md max-w-xs mx-auto">
            <div className="text-lg font-bold text-gray-800 mb-2">{title || "Brak tytułu"}</div>
            <div className="text-sm font-semibold mb-2">
                Kategorie: {category || "Brak kategorii"}
            </div>
            <div className="text-sm text-gray-700 italic mb-2">Data: {formatDate(date)}</div>
            <div className="text-sm font-medium text-gray-700 mb-2">
                Komentarze: {comments !== undefined ? comments : "Brak komentarzy"}
            </div>
            <div className="text-sm font-medium text-gray-700">
                Ocena: {score !== undefined ? score : "Brak oceny"}
            </div>
        </div>
    );
}

export default Thread;
