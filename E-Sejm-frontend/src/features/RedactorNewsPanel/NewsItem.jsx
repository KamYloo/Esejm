import React from "react";

const NewsItem = ({ item, handleEditNews, handleDeleteNews }) => {
    return (
        <li className="flex justify-between items-center border-b py-2">
            <div>
                <h3 className="font-bold">{item.title}</h3>
                {/* Renderowanie content jako HTML */}
                <div
                    className="text-gray-700"
                    dangerouslySetInnerHTML={{ __html: item.content }}
                />
                <span className="text-sm text-gray-500">Kategoria: {item.categories ? item.categories.map(category => category.name).join(', ') : ''}</span>
                {item.imagePath && (
                    <div className="mt-2">
                        <img
                            src={item.imagePath}
                            alt="Podgląd obrazka"
                            className="w-20 h-20 object-cover rounded"
                        />
                    </div>
                )}
            </div>
            <div className="flex items-center space-x-2">
                <button
                    onClick={() => handleEditNews(item.id)}
                    className="px-3 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600"
                >
                    Edytuj
                </button>
                <button
                    onClick={() => handleDeleteNews(item.id)}
                    className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600"
                >
                    Usuń
                </button>
            </div>
        </li>
    );
};

export default NewsItem;
